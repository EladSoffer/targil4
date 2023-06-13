package com.example.friends;

public class Message {
    private String content;

    private String sender;
    private String time;

    public Message(String msg, String t, String s) {
        this.content = msg;
        this.time = t;
        this.sender = s;
    }


    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
