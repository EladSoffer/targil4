package com.example.friends;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button blue = findViewById(R.id.choose_blue);
        Button purple = findViewById(R.id.choose_purple);
        EditText server_port = findViewById(R.id.edit_server_address);
        ImageButton exit = findViewById(R.id.exit_settings);
        exit.setOnClickListener(v-> finish());
    }

}
