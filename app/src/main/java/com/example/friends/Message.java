package com.example.friends;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters({MessageConvertor.class})
public class Message {
   @NonNull
   @PrimaryKey
    private String _id;
    private String content;

    private MesSender sender;
    private String created;
    private String chatId;

    public Message(String content, String created, MesSender sender, String _id) {
        this.content = content;
        this.created = created;
        this.sender = sender;
        this._id = _id;
        this.chatId = null;
    }


    public MesSender getSender() {
        return sender;
    }

    public String getCreated() {
        return created;
    }

    public String getContent() {
        return content;
    }

    public String get_id(){return _id;}

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSender(MesSender sender) {
        this.sender = sender;
    }

    public String getChatId() {
        return chatId;
    }

    public static class MesSender{
        String username;
        String _id;

        public String get_id() {
            return _id;
        }

        public String getUsername() {
            return username;
        }
    }
}