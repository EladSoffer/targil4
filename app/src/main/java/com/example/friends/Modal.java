package com.example.friends;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class Modal extends AppCompatActivity {

    private AppDB db;
    private User user;
    UserDao userDao;
    String username;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "Contacts")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        setContentView(R.layout.add_contact);


        Button addBtn = findViewById(R.id.addButton);
        Button cancelBtn = findViewById(R.id.cancelButton);
        addBtn.setOnClickListener(v -> addConntact());
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void addConntact() {
        //// later check if there is user like that and the user picture
        EditText usernameEditText = findViewById(R.id.new_contact);
        username = usernameEditText.getText().toString();
        user = new User(username,R.drawable.ic_person,null,null);
        userDao.insert(user);
        finish();
    }
}
