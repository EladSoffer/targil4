package com.example.friends;
import androidx.room.TypeConverter;
import com.google.gson.Gson;

public class MessageConvertor {
    @TypeConverter
    public static Message.MesSender fromString(String value) {
        return new Gson().fromJson(value, Message.MesSender.class);
    }

    @TypeConverter
    public static String toString(Message.MesSender sender) {
        return new Gson().toJson(sender);
    }
}

