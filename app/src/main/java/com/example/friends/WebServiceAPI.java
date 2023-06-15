package com.example.friends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WebServiceAPI {
    @GET("Chats")
    Call<List<User>> getContacts(@Header("authorization") String token);
}
