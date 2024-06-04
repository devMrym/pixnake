package com.example.lab5assignment;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.media.MediaPlayer;

public class homepage extends AppCompatActivity {
    TextView helloUser;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



        //sign out button
        ImageButton signOut = (ImageButton)
                findViewById(R.id.SignOutButton);
        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
        final MediaPlayer mp2 = MediaPlayer.create(this , R.raw.signout);
        final MediaPlayer mp3 = MediaPlayer.create(this , R.raw.signoutyes);
        builder= new AlertDialog.Builder(this);
        signOut.setOnClickListener(new
                                          View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mp2.start();
                                                  builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                                                  builder.setMessage("").setCancelable(false)
                                                          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                                                                  mp3.start();
                                                                  dialog.cancel();
                                                                  goToFirstPage();

                                                              }
                                                          }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                                                                  mp1.start();
                                                                  dialog.cancel();

                                                              }
                                                          });

                                                  AlertDialog alert = builder.create();
                                                  alert.setTitle("Are you sure you want to sign out? ");
                                                  alert.show();

                                              }
                                          });

        // play game button
        ImageButton playGame = (ImageButton)
                findViewById(R.id.PlayButton);
        playGame.setOnClickListener(new
                                           View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   mp1.start();
                                                   goToGamePage();
                                               }
                                           });
        // view scores button
        ImageButton Scores = (ImageButton)
                findViewById(R.id.ScoreButton);
        Scores.setOnClickListener(new
                                            View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mp1.start();
                                                    goToScoresPage();
                                                }
                                            });
        // other (coming soon) button
        ImageButton other = (ImageButton)
                findViewById(R.id.OtherButton);
        other.setOnClickListener(new
                                            View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mp1.start();
                                                    goToComingSoon();
                                                }
                                            });

        helloUser = (TextView) findViewById(R.id.helloUser);

        Bundle extras = getIntent().getExtras();
        String username = null;
        if (extras !=null){
            username = extras.getString("username");
            helloUser.setText("Hello " + username+"!");

        }



    }

    private void goToComingSoon() {
        Intent intent = new Intent(this,
                comingsoon.class);
        startActivity(intent);
    }
    private void goToFirstPage() {
        Intent intent = new Intent(this,
                firstpage.class);
        startActivity(intent);
    }

    private void goToGamePage() {
        Intent intent = new Intent(this,
                MainActivity.class);
        startActivity(intent);
    }

    private void goToScoresPage() {
        Intent intent = new Intent(this,
                scorespage.class);
        startActivity(intent);
    }
}