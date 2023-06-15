package com.example.friends;

import androidx.room.TypeConverter;
import com.google.gson.Gson;

public class UserInUserConverter {

    @TypeConverter
    public static User.user fromString(String value) {
        return new Gson().fromJson(value, User.user.class);
    }

    @TypeConverter
    public static String toString(User.user userInUser) {
        return new Gson().toJson(userInUser);
    }

    @TypeConverter
    public static User.Messagetwo fromMessageString(String value) {
        return new Gson().fromJson(value, User.Messagetwo.class);
    }

    @TypeConverter
    public static String toMessageString(User.Messagetwo message) {
        return new Gson().toJson(message);
    }

}

