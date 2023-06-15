package com.example.friends;

import androidx.room.TypeConverter;
import org.bson.types.ObjectId;

public class ObjectIdConverter {
    @TypeConverter
    public static ObjectId fromString(String value) {
        return new ObjectId(value);
    }

    @TypeConverter
    public static String toString(ObjectId objectId) {
        return objectId.toString();
    }
}

