package com.example.lab5assignment;


import androidx.appcompat.app.AppCompatActivity;

import android. content. Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageButton;

import android.media.MediaPlayer;

public class comingsoon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comingsoon);

        ImageButton goBack = (ImageButton)
                findViewById(R.id.Back3Button);
        goBack.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      mp1.start();
                                                      goToLoginActivity();
                                                  }
                                              });
    }


    private void goToLoginActivity() {
        Intent intent = new Intent(this,
                homepage.class);
        startActivity(intent);
    }



}