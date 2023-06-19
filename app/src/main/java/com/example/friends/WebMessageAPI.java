package com.example.friends;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebMessageAPI {

    @GET("Chats/{id}/Messages")
    Call <List<Message>> getMessages(@Header("authorization") String token, @Path("id") String chatId);
    @POST("Chats/{id}/Messages")
    Call <Void> sendMessage(@Header("authorization") String token, @Body Map<String, String> mes,@Path("id") String chatId);
}
