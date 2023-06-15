package com.example.friends;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyUserApi {
    Retrofit retrofit;
    MyUserServiceAPI userServiceAPI;

    public MyUserApi() {

        String apiAddress = "http://10.0.2.2:5000/api/";
        retrofit = new Retrofit.Builder().
                baseUrl(apiAddress).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        userServiceAPI = retrofit.create(MyUserServiceAPI.class);
    }

    public Call<Map<String, String>> sign_in(String username, String password) {
        return userServiceAPI.sign_in(Map.of("username", username, "password", password));
    }

    public Call<Void> signup(String username, String password, String name, String profilePicture) {
        return userServiceAPI.sign_up(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
    }

    public Call<Map<String, String>> get_User_Details(String token, String userId) {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        return userServiceAPI.getUserDetails("Bearer " + new Gson().toJson(tokenMap), userId);
    }

}
