package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class messagesAPI {

    private MutableLiveData<List<Message>> postListData;
    private messageDao dao;
    Retrofit retrofit;
    WebMessageAPI webMessageAPI;
    Context context;

    public messagesAPI(MutableLiveData<List<Message>> m, messageDao d, Context con) {
        this.postListData = m;
        SharedPreferences sharedPreferences = con.getSharedPreferences("server_port", MODE_PRIVATE);
        String server = sharedPreferences.getString("server", "");
        String apiAddress = "http://"+ server +"/api/";
        retrofit = new Retrofit.Builder().
                baseUrl(apiAddress).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        webMessageAPI = retrofit.create(WebMessageAPI.class);
        this.context = con;
        this.dao =d;
    }

    public CompletableFuture<Integer> get(String chatId) {
        CompletableFuture<Integer> future =new CompletableFuture<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Call<List<Message>> call = webMessageAPI.getMessages("Bearer " + new Gson().toJson(tokenMap), chatId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
//                new Thread(() -> {
                    if (response.body() == null) {
                        return;
                    }

                    List<Message> existingMessages = dao.allMessages();
                    List<Message> mesList = response.body();
                    List<Message> messagesToAdd = new ArrayList<>();

                    for (Message serverMessage : mesList) {
                        boolean exists = false;
                        for (Message existingMessage : existingMessages) {
                            if (serverMessage.get_id().equals(existingMessage.get_id())) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            serverMessage.setChatId(chatId);
                            messagesToAdd.add(serverMessage);
                            existingMessages.add(serverMessage);
                        }
                    }

                    // Insert the new messages
                    if (!messagesToAdd.isEmpty()) {
                        Collections.reverse(messagesToAdd);
                        for (Message mes : messagesToAdd) {
                            dao.insert(mes);
                        }
                    }
                    future.complete(200);
//                }).start();
            }


            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("API Call", "Failed: " + t.getMessage());
                future.complete(-1);
            }
        });
        return future;
    }
    public CompletableFuture<Integer> insert(String message, String chatId){
        CompletableFuture<Integer> future =new CompletableFuture<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Call<Void> call = webMessageAPI.sendMessage("Bearer " + new Gson().toJson(tokenMap), Map.of("msg",message),chatId );
        call.enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    get(chatId);
                    future.complete(1);
                } else {
                    Log.e("else",response.message());
                    Log.e("else",response.toString());
                    future.complete(-1);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                future.complete(-1);
               Log.e("problem","problem");
            }

        });
        return future;
    }
}
