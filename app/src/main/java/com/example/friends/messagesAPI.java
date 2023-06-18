package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String apiAddress = "http://10.0.2.2:5001/api/";
        retrofit = new Retrofit.Builder().
                baseUrl(apiAddress).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        webMessageAPI = retrofit.create(WebMessageAPI.class);
        this.context = con;
        this.dao =d;
    }

    public void get(String chatId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        Call<List<Message>> call = webMessageAPI.getMessages("Bearer " + new Gson().toJson(tokenMap), chatId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                new Thread(() -> {
                    // Delete all existing messages
                    List<Message> existingMessages = dao.allMessages();

                    for (Message mes : existingMessages) {
                        dao.delete(mes);
                    }
                    // Insert new list of messges
                    List<Message> mesList = response.body();
                    for (Message mes : mesList) {
                        dao.insert(mes);
                    }
                    postListData.postValue(dao.allMessages());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("API Call", "Failed: " + t.getMessage());
            }
        });
    }
}
