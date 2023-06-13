package com.example.friends;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Button gray = findViewById(R.id.choose_gray);
        Button purple = findViewById(R.id.choose_purple);
        EditText server_port = findViewById(R.id.edit_server_address);
        ImageButton exit = findViewById(R.id.exit_settings);
        exit.setOnClickListener(v -> finish());

        gray.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate(); // Restart the activity to apply the new theme
        });

        purple.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate(); // Restart the activity to apply the new theme
        });
    }
}

