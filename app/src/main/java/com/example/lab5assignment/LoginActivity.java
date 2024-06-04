package com.example.lab5assignment;


// Importing necessary classes from the Android framework and the current app package.
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.media.MediaPlayer;

// LoginActivity class that extends AppCompatActivity, providing support for activities on Android.
public class LoginActivity extends AppCompatActivity {

    // Declaration of UI elements and a helper class variable.
    ImageButton btnLogin, btnBack; // Button for logging in.
    EditText edtUsername; // Text field for the user to enter their username.
    EditText edtPassword; // Text field for the user to enter their password.
    DatabaseHelper databaseHelper; // Helper class for database operations, such as checking user existence.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the content view to the layout defined in login_activity.xml.
        setContentView(R.layout.activity_login);

        // Linking the UI elements defined in XML with the Java code.
        btnLogin = (ImageButton) findViewById(R.id.LoginButton);
        btnBack = (ImageButton) findViewById(R.id.BackButton);
        edtUsername = (EditText) findViewById(R.id.edtScore);
        edtPassword = (EditText) findViewById(R.id.edtName);
        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
        final MediaPlayer mp2 = MediaPlayer.create(this , R.raw.ahhhh);
        btnBack.setOnClickListener(new
                                          View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mp1.start();
                                                  goToFirstPage();
                                              }
                                          });



        // Initializing the database helper object for database operations.
        databaseHelper = new DatabaseHelper(LoginActivity.this);
        // Setting an onClickListener for the login button to handle user clicks.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
                // Checking if the user exists in the database with the provided username and password.
                boolean isExist = databaseHelper.checkUserExist(edtUsername.getText().toString(), edtPassword.getText().toString());

                if (isExist){
                    // If user exists, navigate to homepage and pass the username as an extra in the intent.
                    Intent intent = new Intent(LoginActivity.this, homepage.class);
                    intent.putExtra("username", edtUsername.getText().toString());
                    startActivity(intent); // Starting the homepage.
                }
                else {
                    // If user does not exist, clear the password field and show a login failure message.
                    edtPassword.setText(null);
                    Toast.makeText(LoginActivity.this, "Login failed. Invalid username or password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goToFirstPage() {
        Intent intent = new Intent(this,
                firstpage.class);
        startActivity(intent);
    }

}
