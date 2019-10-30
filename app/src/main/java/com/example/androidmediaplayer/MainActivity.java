package com.example.androidmediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button newSongB;
    ListView songsList;
    SongsDB songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newSongB = (Button) findViewById(R.id.newSongBtn);
        songsList = (ListView) findViewById(R.id.songsList);
        songs = new SongsDB(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<String> titles = new ArrayList<>();
        titles = songs.getTitles();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titles);
        songsList.setAdapter(adapter);
        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,ViewSong.class);
                intent.putExtra("title",title);
                intent.putExtra("old","old");
                int REQUEST_ID=13;
                startActivityForResult(intent,REQUEST_ID);
            }
        });
    }

    public void newSong(View v)
    {
        Intent intent = new Intent(this, Song.class);
        startActivity(intent);
    }
}

