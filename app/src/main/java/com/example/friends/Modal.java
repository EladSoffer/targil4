package com.example.friends;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Modal extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        EditText usernameEditText = findViewById(R.id.username);
        String username = usernameEditText.getText().toString();
        Button addBtn = findViewById(R.id.addButton);
        Button cancelBtn = findViewById(R.id.cancelButton);
        addBtn.setOnClickListener(v -> addConntact(username));
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void addConntact(String username) {
        // Save the user details to a database or send them to a server
        // Implement your logic here
    }
}
