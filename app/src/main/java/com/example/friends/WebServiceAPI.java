package com.example.friends;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceAPI {
    @GET("Chats")
    Call<List<User>> getContacts(@Header("authorization") String token);

    @POST("Chats")
    Call<Void> addUser(@Header("authorization") String token, @Body Map<String, String> user);

}
