package com.example.friends;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyUserServiceAPI {
    @POST("Tokens")
    Call<Map<String, String>> sign_in(@Body Map<String, String> user);

    @POST("Users")
    Call<Void> sign_up(@Body Map<String, String> user);

    @GET("Users/{id}")
    Call<Map<String, String>> getUserDetails(@Header("authorization") String token, @Path("id") String userId);

    @GET("/api/Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("authorization") String token, @Path("id") String userId);
}
