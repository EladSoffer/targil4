package com.example.friends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class chat extends AppCompatActivity {
    final private String[] messages = {
            "Hi, how are you?", "good", "you?", "great","Hi, how are you?", "good", "you?", "great","Hi, how are you?", "good", "you?", "great"
    };
    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59","12:00", "00:30", "03:23", "08:59","12:00", "00:30", "03:23", "03:05"
    };
    final private String[] senders = {
            "me","him","him","me","me","him","him","me","me","him","him","me"
    };

    ImageView profile_pic;
    TextView user_name;

    ListView mess;
    ChatListAdapter chat_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);
        profile_pic = findViewById(R.id.user_image_profile_image);
        user_name = findViewById(R.id.user_text_user_name);

        Intent activity = getIntent();

        if (activity != null){
            String picture = activity.getStringExtra("profilePicture");
            if (picture != null && picture.startsWith("data:image/jpeg;base64,")) {
                picture = picture.replace("data:image/jpeg;base64,", "");
                byte[] imageBytes = Base64.decode(picture, Base64.DEFAULT);
                Bitmap myPic = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profile_pic.setImageBitmap(myPic);
            }
            String name = activity.getStringExtra("userName");
            user_name.setText(name);
        }
        ImageButton exit = findViewById(R.id.exit_chat);
        exit.setOnClickListener(v -> finish());

        ArrayList<Message> chat_messages = new ArrayList<>();

        for (int i = 0; i < senders.length; i++) {
            Message mes = new Message(
                    messages[i], times[i],
                    senders[i]
            );

            chat_messages.add(mes);
        }
        mess = findViewById(R.id.msg_list);
        chat_adapter = new ChatListAdapter(getApplicationContext(), chat_messages);

        mess.setAdapter(chat_adapter);
        mess.setClickable(true);
        scrollToLastMessage();
    }

    private void scrollToLastMessage() {
        mess.post(() -> {
            int lastItemPosition = mess.getAdapter().getCount() - 1;
            mess.smoothScrollToPosition(lastItemPosition);
        });
    }
}

