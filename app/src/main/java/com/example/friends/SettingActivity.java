package com.example.friends;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button gray = findViewById(R.id.choose_gray);
        Button purple = findViewById(R.id.choose_purple);
        ImageButton exit = findViewById(R.id.exit_settings);
        exit.setOnClickListener(v -> finish());
        Button change_server_add = findViewById(R.id.change_server_add);
        change_server_add.setOnClickListener(a-> changeServer());

        gray.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate(); // Restart the activity to apply the new theme
        });

        purple.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate(); // Restart the activity to apply the new theme
        });
    }
    public void changeServer(){
        EditText edit_server_address = findViewById(R.id.edit_server_address);
        SharedPreferences sh =getApplicationContext().getSharedPreferences("server_port",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("server",edit_server_address.getText().toString());
        editor.apply();
        Toast.makeText(getApplicationContext(),"changed",Toast.LENGTH_SHORT).show();

    }
}

