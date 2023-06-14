package com.example.friends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private void getDitaiels() {

        // Validate input

        MyUserApi userApi = new MyUserApi();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String username = sharedPreferences.getString("userName", "");
        Call<Map<String, String>> call = userApi.get_User_Details(token, username);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                if (response.isSuccessful()) {
                    Map<String, String> responseBody = response.body();

                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

