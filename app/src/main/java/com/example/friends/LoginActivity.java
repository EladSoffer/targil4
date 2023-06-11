package com.example.friends;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button loginBtn = findViewById(R.id.log_btn);
        loginBtn.setOnClickListener(v -> login());


        TextView notRegistered = findViewById(R.id.Not_registered);
        notRegistered.setOnClickListener(v -> {
            // Open registration activity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    private void login(){
        EditText usernameEditText = findViewById(R.id.username_log);
        EditText passwordEditText = findViewById(R.id.password_log);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(password.length() < 8){
            Toast.makeText(this,"Password must be at list 8 chars",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( !(password.matches(".*[A-Z].*"))){
            Toast.makeText(this,"Password must contain at list one big letter",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
        startActivity(intent);


    }
}
