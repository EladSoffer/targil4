package com.example.friends;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;

public class ChatListAdapter extends ArrayAdapter<Message> {
    LayoutInflater inflater;
    Context context;
    public ChatListAdapter(Context ctx, ArrayList<Message> messages) {
        super(ctx, R.layout.message,messages);
        this.context = ctx;

        this.inflater = LayoutInflater.from(ctx);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("token", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");
        Message m = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.message, parent, false);
        }
        TextView text = convertView.findViewById(R.id.msg);
        text.setText(m.getContent());
        RelativeLayout layout = convertView.findViewById(R.id.msg_layout);
        LinearLayout l = convertView.findViewById(R.id.full_layout);
        TextView time = convertView.findViewById(R.id.msg_time);
        String hour = m.getCreated().substring(11, 16);


        if (Objects.equals(m.getSender().getUsername(), username)){
            l.setGravity(Gravity.START);
            time.setText(hour);
            layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_sender));

        }
        else {
            l.setGravity(Gravity.END);
            time.setText(hour);
            layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_reciver));
        }


        return convertView;
    }

}