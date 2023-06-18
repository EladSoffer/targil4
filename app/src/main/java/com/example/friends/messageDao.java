package com.example.friends;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface messageDao {
    @Query("SELECT * FROM message")
    List<Message> allMessages();

//    @Query("SELECT * FROM message WHERE id = :id")
//    Message get(int id);

    @Insert
    void insert(Message... posts);

    @Update
    void update(Message... posts);

    @Delete
    void delete(Message... messages);

}
