package com.example.friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WebMessageAPI {

    @GET("Chats/{id}/Messages")
    Call <List<Message>> getMessages(@Header("authorization") String token, @Path("id") String chatId);
}
