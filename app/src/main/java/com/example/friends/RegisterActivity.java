package com.example.friends;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private ImageView selectedImage;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText displayNameEditText;

    private Uri selectedImageUri;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedImage = findViewById(R.id.selected_image);
        usernameEditText = findViewById(R.id.edit_username_reg);
        passwordEditText = findViewById(R.id.edit_password_reg);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        displayNameEditText = findViewById(R.id.display_name_input);

        Button registerButton = findViewById(R.id.reg_btn);
        registerButton.setOnClickListener(v -> registerUser());
        TextView alreadyRegister = findViewById(R.id.already_register);
        alreadyRegister.setOnClickListener(v -> finish());



        // Initialize the image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        selectedImageUri = result;
                        displaySelectedImage();
                    }
                }
        );
    }

    private void registerUser() {
        // Get user details
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String displayName = displayNameEditText.getText().toString();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || displayName.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password and confirm password match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the user details and the selected image
        saveUserDetails(username, password, displayName);
        if (selectedImageUri != null) {
            saveImage(selectedImageUri);
        }

        // Display a toast or perform any other action to indicate successful registration
        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
    }

    public void onAddPictureButtonClick(View view) {
        imagePickerLauncher.launch("image/*");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            displaySelectedImage();
        }
    }

    private void displaySelectedImage() {
        if (selectedImageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                if (bitmap != null) {
                    // Resize the bitmap to maximum dimensions of 200x200 while preserving aspect ratio
                    int maxWidth = 300;
                    int maxHeight = 300;

                    int imageWidth = bitmap.getWidth();
                    int imageHeight = bitmap.getHeight();

                    if (imageWidth > 0 && imageHeight > 0) {
                        float scale = Math.min(((float) maxWidth / imageWidth), ((float) maxHeight / imageHeight));
                        Matrix matrix = new Matrix();
                        matrix.postScale(scale, scale);
                        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, imageWidth, imageHeight, matrix, true);

                        selectedImage.setImageBitmap(resizedBitmap);
                        selectedImage.setVisibility(View.VISIBLE);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUserDetails(String username, String password, String displayName) {
        // Save the user details to a database or send them to a server
        // Implement your logic here
    }

    private void saveImage(Uri imageUri) {
        // Save the image using the selectedImageUri
        // Implement your image-saving logic here
    }
}
