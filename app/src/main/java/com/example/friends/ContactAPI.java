package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactAPI {

    private MutableLiveData<List<User>> postListData;
    private UserDao dao;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    Context context;



    public ContactAPI(MutableLiveData<List<User>> postListData, UserDao dao ,Context con) {
        SharedPreferences sharedPreferences = con.getSharedPreferences("server_port", MODE_PRIVATE);
        String server = sharedPreferences.getString("server", "");
        String apiAddress = "http://"+ server +"/api/";
        this.postListData = postListData;
        this.dao = dao;
        retrofit = new Retrofit.Builder().
                baseUrl(apiAddress).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.context = con;
    }

    public CompletableFuture<Integer> get() {
        CompletableFuture<Integer> future =new CompletableFuture<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        Call<List<User>> call = webServiceAPI.getContacts("Bearer " + new Gson().toJson(tokenMap));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                new Thread(() -> {
                    if (response.body() == null){
                        return;
                    }
                    // Delete all existing users
                    List<User> existingUsers = dao.allUsers();

                    for (User user : existingUsers) {
                        dao.delete(user);

                    }

                    // Insert new list of users
                    List<User> userList = response.body();
                    if (userList.isEmpty()){
                        return;
                    }

                    for (User user : userList) {
                        dao.insert(user);
                    }

                    postListData.postValue(dao.allUsers());
                    future.complete(1);
                }).start();

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                    future.complete(-1);
            }
        });
        return future;
    }


    public CompletableFuture<Integer> insert(String user) {
        CompletableFuture<Integer> s = new CompletableFuture<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        Call<Void> call = webServiceAPI.addUser("Bearer " + new Gson().toJson(tokenMap), Map.of("username", user));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    s.complete(1);
                } else {
                    Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show();
                    s.complete(-1);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show();
                s.complete(-1);
            }
        });
        return s;
    }


    public void delete(User user) {
    }



}