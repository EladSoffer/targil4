package com.example.friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    CustomListAdapter adapter;
    private ArrayList<User> users;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        FloatingActionButton settingsBtn = findViewById(R.id.btnSettings);
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        FloatingActionButton addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ListActivity.this, Modal.class);
            startActivity(intent);
        });

        FloatingActionButton user_exit = findViewById(R.id.logoutBtn);
        user_exit.setOnClickListener(v -> finish());

        userViewModel = new UserViewModel(getApplicationContext());
        users = new ArrayList<>();
        adapter = new CustomListAdapter(getApplicationContext(), users);

        initializeListView();

        ImageView userImageProfile = findViewById(R.id.user_image_profile_image);
        userImageProfile.setImageResource(R.drawable.neymar);
    }

    private void initializeListView() {
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        // Handle item click events
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            User selectedUser = users.get(i);
            Intent intent = new Intent(getApplicationContext(), chat.class);
            intent.putExtra("userName", selectedUser.getUserName());
            intent.putExtra("profilePicture", selectedUser.getPictureId());
            intent.putExtra("lastMassage", selectedUser.getLastMassage());
            intent.putExtra("time", selectedUser.getLastMassageSendingTime());
            startActivity(intent);
        });

        // Handle item long click events
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            User selectedUser = users.get(i);
            userViewModel.delete(selectedUser);

            return true; // Return true to consume the long press event
        });

        // Observe the user list from the ViewModel
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                users.clear();
                users.addAll(userList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.getUsers();
        userViewModel.getUsers().observe(this, userList -> {
            users.clear();
            users.addAll(userList);
            adapter.notifyDataSetChanged();
        });
    }
}

