package com.example.friends;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Modal extends AppCompatActivity {


    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_contact);


        userViewModel = new UserViewModel(getApplicationContext());

        Button addBtn = findViewById(R.id.addButton);
        Button cancelBtn = findViewById(R.id.cancelButton);

        addBtn.setOnClickListener(v -> addContact());
        cancelBtn.setOnClickListener(v -> finish());
    }

    private void addContact() {
        EditText usernameEditText = findViewById(R.id.new_contact);
        String username = usernameEditText.getText().toString();
        if (username.isEmpty()) {
            // Show error message for 5 seconds
            usernameEditText.setError("Username cannot be empty");

            new Handler().postDelayed(() -> {
                usernameEditText.setError(null);
            }, 5000);
            return;
        }
        userViewModel.add(username);
        finish();
    }
}

