package com.example.lab5assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android. content. Intent;
import android.os.Bundle;
import android.database.sqlite. SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.google.android.material.color.utilities.Score;

public class SaveScore extends AppCompatActivity implements View.OnClickListener  {

    Controllerdb db =new Controllerdb(this);
    SQLiteDatabase database;
    EditText Name, Score;
    ImageButton Submitdatabtn, Showdatabtn, backButton;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);
builder = new AlertDialog.Builder(this);


        Name = (EditText) findViewById(R.id.edtName);
        Score = (EditText) findViewById(R.id.edtScore);
        Submitdatabtn = (ImageButton) findViewById(R.id.savebutton);
        Showdatabtn = (ImageButton) findViewById(R.id.vas);
        Submitdatabtn.setOnClickListener(this);
        Showdatabtn.setOnClickListener(this);

        ImageButton backToGame = (ImageButton)
                findViewById(R.id.btg);
        backToGame.setOnClickListener(new
                                              View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View v) {
                                                      mp1.start();
                                                      goToGame();
                                                  }
                                              });

    }

    @Override
    public void onClick (View v)
    {        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
             final MediaPlayer mp2 = MediaPlayer.create(this , R.raw.popup);
        if (v.getId() == R.id.savebutton) {
            mp2.start();
            database = db.getWritableDatabase();
          database.execSQL("INSERT INTO UserDetails (Name, Score)VALUES (' " + Name.getText() + "' , '" + Score.getText() + "')");

            builder.setMessage("Go back to homepage ?")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mp1.start();
                            dialog.cancel();
                            goToHome();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mp1.start();
                            dialog.cancel();

                        }
                    });

            AlertDialog alert = builder.create();
            alert.setTitle("Your Score Has Been Saved!");
            alert.show();




          //  Toast.makeText(this, "Score Has Been Saved!", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.vas) {
            Intent intent = new Intent(this, scorespage.class);
            startActivity(intent);


        }
    }

    private void goToGame() {
        Intent intent = new Intent(this,
                MainActivity.class);
        startActivity(intent);
    }

    private void goToViewScores() {
        Intent intent = new Intent(this,
                scorespage.class);
        startActivity(intent);
    }

    private void goToHome() {
        Intent intent = new Intent(this,
                homepage.class);
        startActivity(intent);
    }

}