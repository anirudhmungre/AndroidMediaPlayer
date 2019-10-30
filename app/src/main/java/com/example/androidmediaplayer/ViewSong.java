package com.example.androidmediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ViewSong extends AppCompatActivity {

    EditText title;
    EditText url;
    Button back;
    Intent intent;
    SongsDB songsDB;
    String old_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_song);
        intent = getIntent();
        title = (EditText) findViewById(R.id.title);
        url = (EditText) findViewById(R.id.url);
        back = (Button) findViewById(R.id.back);
        old_title = intent.getStringExtra("title");
        SongClass song = new SongClass();
        songsDB = new SongsDB(this);
        song = songsDB.getSong(old_title);
        title.setText(song.getTitle());
        url.setText(song.getUrl());
    }

    public void save(View v) {
        songsDB.updateSong(old_title, title.getText().toString(), url.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    public void delete(View v) {
        songsDB.removeSong(old_title);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void back(View v) {
        setResult(RESULT_OK,intent);
        finish();
    }
}
