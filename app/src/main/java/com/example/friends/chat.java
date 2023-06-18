package com.example.friends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class chat extends AppCompatActivity {

    ImageView profile_pic;
    TextView user_name;
    private ArrayList<Message> messages1;
    ListView mess;
    ChatListAdapter chat_adapter;

    private MessageViewModel messageViewModel;

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


        messageViewModel = new MessageViewModel(getApplicationContext());

        messages1 = new ArrayList<>();

        chat_adapter = new ChatListAdapter(getApplicationContext(), messages1);

        initializeListView();

        scrollToLastMessage();
    }

    private void scrollToLastMessage() {
        mess.post(() -> {
            int lastItemPosition = mess.getAdapter().getCount() - 1;
            mess.smoothScrollToPosition(lastItemPosition);
        });
    }


    private void initializeListView() {
        mess = findViewById(R.id.msg_list);
        mess.setAdapter(chat_adapter);


        // Observe the user list from the ViewModel
        messageViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messages1.clear();
                messages1.addAll(messages);
                chat_adapter.notifyDataSetChanged();
            }
        });
    }

}

