package com.example.friends;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> allUsers();

    @Query("SELECT * FROM user WHERE id = :id")
    User get(int id);

    @Insert
    void insert(User... posts);

    @Update
    void update(User... posts);

    @Delete
    void delete(User... posts);
}