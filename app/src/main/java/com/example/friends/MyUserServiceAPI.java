package com.example.friends;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyUserServiceAPI {
    @POST("Users")
    Call<Map<String, String>> sign_in(@Body Map<String, String> user);

    @POST("Users")
    Call<Void> sign_up(@Body Map<String, String> user);
}
