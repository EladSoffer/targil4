package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

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
//    public void add(String message){
//        messagesAPI.insert(message);
//        messageListData.postValue(dao.allMessages);
//    }
    class messageListData extends MutableLiveData<List<Message>>{
        public messageListData(){
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() ->{
                try {
                    Thread.sleep(500);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("contactID", MODE_PRIVATE);
                    String chatId = sharedPreferences.getString("contactID", "");
                    messagesAPI.get(chatId);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public LiveData<List<Message>> getAll(){return messageListData;}
}
