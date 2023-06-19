package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class MesRepository {
    private messageDao dao;
    private messageListData messageListData;
    private messagesAPI messagesAPI;
    Context context;

    public MesRepository(Context context) {
        AppDB db = Room.databaseBuilder(context, AppDB.class, "Messages").allowMainThreadQueries().build();
        dao = db.messageDao();
        messageListData = new messageListData();
        messagesAPI = new messagesAPI(messageListData, dao, context);
        this.context = context;
    }
    public void add(String message){
        SharedPreferences sharedPreferences = context.getSharedPreferences("contactID", MODE_PRIVATE);
        String chatId = sharedPreferences.getString("contactID", "");
        CompletableFuture<Integer> future=messagesAPI.insert(message,chatId);
        future.thenAccept(v->{
            CompletableFuture<Integer> s =messagesAPI.get(chatId);
            s.thenAccept(a->{
                List<Message> l = dao.allMessages();
                List<Message> good = new ArrayList<>(); // Initialize 'good' as an empty ArrayList
                for (Message m : l) {
                    if (Objects.equals(m.getChatId(), chatId)) {
                        good.add(m);
                    }
                }
                messageListData.postValue(good);
            });

        });





    }
    class messageListData extends MutableLiveData<List<Message>>{
        public messageListData(){
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
//            new Thread(() -> {
                SharedPreferences sharedPreferences = context.getSharedPreferences("contactID", MODE_PRIVATE);
                String chatId = sharedPreferences.getString("contactID", "");
                CompletableFuture<Integer> future = messagesAPI.get(chatId);
                future.thenAccept(v ->{
                    List<Message> l = dao.allMessages();
                    List<Message> good = new ArrayList<>(); // Initialize 'good' as an empty ArrayList
                    for (Message m : l) {
                        if (Objects.equals(m.getChatId(), chatId)) {
                            good.add(m);
                        }
                    }
                    messageListData.postValue(good);
                });

//            }).start();

        }

    }
    public LiveData<List<Message>> getAll(){return messageListData;}
}
