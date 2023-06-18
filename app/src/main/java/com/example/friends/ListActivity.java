package com.example.friends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

    Bitmap myPic;
    String myName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDetails();
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


    }

    private void initializeListView() {
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        // Handle item click events
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            User selectedUser = users.get(i);
            Intent intent = new Intent(getApplicationContext(), chat.class);
            intent.putExtra("userName", selectedUser.getUser().getDisplayName());
            ///check
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("profilePicture", MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("profilePicture", selectedUser.getUser().getProfilePic());
            edit.apply();

            SharedPreferences s =getApplicationContext().getSharedPreferences("contactID",MODE_PRIVATE);
            SharedPreferences.Editor editor = s.edit();
            editor.putString("contactID",selectedUser.getId());
            editor.apply();

            startActivity(intent);
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

    private void getDetails() {

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
                    String name = responseBody.get("displayName");
                    String picture = responseBody.get("profilePic");

                    // Remove the data URL prefix if present
                    if (picture.startsWith("data:image/jpeg;base64,")) {
                        picture = picture.replace("data:image/jpeg;base64,", "");
                    }

                    Toast.makeText(ListActivity.this, "Hello " +name, Toast.LENGTH_SHORT).show();
                    byte[] imageBytes = Base64.decode(picture, Base64.DEFAULT);
                    myPic = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    myName = name;

                    ImageView userImageProfile = findViewById(R.id.user_image_profile_image);
                    userImageProfile.setImageBitmap(myPic);

                    TextView user_text_user_name = findViewById(R.id.user_text_user_name);
                    user_text_user_name.setText(myName);
                }
            }


            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Get details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}