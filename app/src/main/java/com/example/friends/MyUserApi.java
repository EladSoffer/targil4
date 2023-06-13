package com.example.friends;

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

    public Call<Map<String, String>> sign_in(String username, String password, String token) {
        return userServiceAPI.sign_in(Map.of("username", username, "password", password, "firebaseToken", token));
    }

    public Call<Void> signup(String username, String password, String name, String profilePicture) {
        return userServiceAPI.sign_up(Map.of("username", username, "password", password, "displayName", name, "profilePic", profilePicture));
    }
}
