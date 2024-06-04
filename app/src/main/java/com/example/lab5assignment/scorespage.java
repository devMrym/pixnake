package com.example.lab5assignment;


import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget. ListView;
import android.widget.Toast;
import android.media.MediaPlayer;

import java.util.ArrayList;
public class scorespage extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    Controllerdb controllerdb = new Controllerdb ( this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String> ();
    private ArrayList<String> Score = new ArrayList<String> ();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer mp1 = MediaPlayer.create(this , R.raw.updownleftright);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        lv = (ListView) findViewById(R.id.lstvw);

        Button btn = (Button) findViewById(R.id.btnSortBy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
                PopupMenu popup = new PopupMenu(scorespage.this, v);
                popup.setOnMenuItemClickListener(scorespage.this);
                popup.inflate(R.menu.activity_scoree);
                popup.show();
            }
        });
        ImageButton BackButton = (ImageButton) findViewById(R.id.BackToHomePageButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp1.start();
                goToHomeActivity();
            }
        });
    }


    @Override
    @SuppressLint("Range")
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        if (item.getItemId() == R.id.highest) {
            db = controllerdb.getReadableDatabase();
            Cursor cursor = db.rawQuery ( "SELECT * FROM UserDetails ORDER BY Score DESC", null);
            Id.clear ();
            Name. clear ();
            Score.clear ();
            if (cursor.moveToFirst ()) {
                do {
                    Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                    Name.add(cursor.getString(cursor.getColumnIndex("Name")));
                    Score.add(cursor.getString(cursor.getColumnIndex("Score")));
                } while (cursor.moveToNext());
            }
            CustomAdapter ca = new CustomAdapter ( scorespage.this, Id, Name, Score) ;
            lv.setAdapter (ca);
//code to set adapter to populate list

            cursor.close();
            return true;
        }else if (item.getItemId() == R.id.lowest) {
            db = controllerdb.getReadableDatabase();
            Cursor cursor = db.rawQuery ( "SELECT * FROM UserDetails ORDER BY Score", null);
            Id.clear ();
            Name. clear ();
            Score.clear ();
            if (cursor.moveToFirst ()) {
                do {
                    Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                    Name.add(cursor.getString(cursor.getColumnIndex("Name")));
                    Score.add(cursor.getString(cursor.getColumnIndex("Score")));
                } while (cursor.moveToNext());
            }
            CustomAdapter ca = new CustomAdapter ( scorespage.this, Id, Name, Score) ;
            lv.setAdapter (ca);
//code to set adapter to populate list

            cursor.close();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(this,
                homepage.class);
        startActivity(intent);
    }


    @Override
    protected void onResume () {
        displayData();
        super.onResume();
    }

    @SuppressLint("Range")
    private void displayData () {
        db = controllerdb.getReadableDatabase();
        Cursor cursor = db.rawQuery ( "SELECT * FROM UserDetails", null);
        Id.clear ();
        Name. clear ();
        Score.clear ();
        if (cursor.moveToFirst ()) {
            do {
                Id.add(cursor.getString(cursor.getColumnIndex("Id")));
                Name.add(cursor.getString(cursor.getColumnIndex("Name")));
                Score.add(cursor.getString(cursor.getColumnIndex("Score")));
            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter ( scorespage.this, Id, Name, Score) ;
        lv.setAdapter (ca);
//code to set adapter to populate list

        cursor.close();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}