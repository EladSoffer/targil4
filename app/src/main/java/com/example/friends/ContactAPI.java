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
        this.postListData = postListData;
        this.dao = dao;
        String apiAddress = "http://10.0.2.2:5001/api/";
        retrofit = new Retrofit.Builder().
                baseUrl(apiAddress).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.context = con;
    }

    public void get() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        Call<List<User>> call = webServiceAPI.getContacts("Bearer " + new Gson().toJson(tokenMap));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                new Thread(() -> {
                    // Delete all existing users
                    List<User> existingUsers = dao.allUsers();

                    for (User user : existingUsers) {
                        dao.delete(user);

                    }

                    // Insert new list of users
                    List<User> userList = response.body();

                    for (User user : userList) {
                        dao.insert(user);
                    }

                    postListData.postValue(dao.allUsers());
                }).start();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }


    public void insert(String user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        Call<Void> call = webServiceAPI.addUser("Bearer " + new Gson().toJson(tokenMap), Map.of("username", user));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    //get();
                } else {
                    Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void delete(User user) {
    }



}