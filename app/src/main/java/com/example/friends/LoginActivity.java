package com.example.friends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences sh =getApplicationContext().getSharedPreferences("server_port",MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("server","10.0.2.2:5000");
        editor.apply();

        Button loginBtn = findViewById(R.id.log_btn);
        loginBtn.setOnClickListener(v -> login());


        TextView notRegistered = findViewById(R.id.Not_registered);
        notRegistered.setOnClickListener(v -> {
            // Open registration activity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        FloatingActionButton settingsBtn = findViewById(R.id.btnSettings);
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent);
        });
    }
    private void login(){
        EditText usernameEditText = findViewById(R.id.username_log);
        EditText passwordEditText = findViewById(R.id.password_log);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(password.length() < 1){
            Toast.makeText(this,"Password must be at list 8 chars",Toast.LENGTH_SHORT).show();
            return;
        }
        if ( !(password.matches(".*[A-Z].*"))){
            Toast.makeText(this,"Password must contain at list one big letter",Toast.LENGTH_SHORT).show();
            return;
        }

        loginUser(username, password);




    }

    private void loginUser(String username,String password) {

        // Validate input

        MyUserApi userApi = new MyUserApi(getApplicationContext());
        Call<Map<String, String>> call = userApi.sign_in(username, password);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                if (response.isSuccessful()) {
                    Map<String, String> responseBody = response.body();
                    if (responseBody != null && responseBody.containsKey("token")) {
                        String token = responseBody.get("token");
                        SharedPreferences sh =getApplicationContext().getSharedPreferences("token",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("token",token);
                        editor.putString("userName", username);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                        startActivity(intent);
                        EditText usernameEditText = findViewById(R.id.username_log);
                        EditText passwordEditText = findViewById(R.id.password_log);
                        usernameEditText.setText("");
                        passwordEditText.setText("");

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
