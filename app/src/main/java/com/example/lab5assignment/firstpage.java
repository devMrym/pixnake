package com.example.lab5assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.media.AudioManager;
import android.media.MediaPlayer;


public class firstpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        ImageButton yourButton = (ImageButton)
                findViewById(R.id.StartButton);
        final MediaPlayer mp = MediaPlayer.create(this , R.raw.sample);
        yourButton.setOnClickListener(new
                                              View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      mp.start();
                                                      goToLoginActivity();
                                                  }
                                              });
    }


    private void goToLoginActivity() {
        Intent intent = new Intent(this,
                LoginActivity.class);
        startActivity(intent);
    }



}