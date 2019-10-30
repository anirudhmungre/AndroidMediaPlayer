package com.example.androidmediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Song extends AppCompatActivity {
    Button saveB;
    EditText titleT;
    EditText urlT;
    SongsDB songsDB;
    SongClass song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        songsDB = new SongsDB(this);
        saveB = (Button) findViewById(R.id.saveBtn);
        titleT = (EditText) findViewById(R.id.titleTxt);
        urlT = (EditText)findViewById(R.id.urlTxt);
    }
    public void save(View v)
    {
        String title = titleT.getText().toString();
        String url = urlT.getText().toString();
        song = new SongClass(title, url);
        songsDB.addSong(song);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
