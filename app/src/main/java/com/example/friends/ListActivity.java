package com.example.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {


    ListView listView;
    CustomListAdapter adapter;
    private AppDB db;
    private ArrayList<User> users;

    private UserDao userDao;

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

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "Contacts")
                .allowMainThreadQueries().build();

        userDao = db.userDao();
        handleUsers();
        ImageView userImageProfile = findViewById(R.id.user_image_profile_image);
        userImageProfile.setImageResource(R.drawable.neymar);

    }

    private void handleUsers() {
        listView = findViewById(R.id.list_view);
        users = new ArrayList<>();
        adapter = new CustomListAdapter(getApplicationContext(), users);
        loadContacts();
        listView.setAdapter(adapter);
        listView.setClickable(true);

        /**

         get into contact after pressing.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = users.get(i);
                Intent intent = new Intent(getApplicationContext(), chat.class);

                intent.putExtra("userName", selectedUser.getUserName());
                intent.putExtra("profilePicture", selectedUser.getPictureId());
                intent.putExtra("lastMassage", selectedUser.getLastMassage());
                intent.putExtra("time", selectedUser.getLastMassageSendingTime());

                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            User selectedUser = users.get(i);
            userDao.delete(selectedUser); // Remove the selected user from the database
            users.remove(i); // Remove the user from the users list
            adapter.notifyDataSetChanged(); // Update the ListView

            return true; // Return true to consume the long press event
        });

    }

    private void loadContacts() {
        users.clear();
        users.addAll(userDao.allUsers());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }
}
