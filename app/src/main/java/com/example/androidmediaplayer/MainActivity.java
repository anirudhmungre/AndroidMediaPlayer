package com.example.androidmediaplayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button newSongB;
    ListView songsList;
    SongsDB songs;
    EditText songsSearch;
    ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newSongB = (Button) findViewById(R.id.newSongBtn);
        songsList = (ListView) findViewById(R.id.songsList);
        songs = new SongsDB(this);
        songsSearch = (EditText) findViewById(R.id.songsSearch);
        titles = new ArrayList<>();
        songsSearch.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                titles = songs.getTitles();
                ArrayList<String> filteredTitles = new ArrayList<>();
                for (String title: titles) {
                    if (title.toLowerCase().contains(songsSearch.getText().toString().toLowerCase())) {
                        filteredTitles.add(title);
                    }
                }
                titles = filteredTitles;
                setupSongsList();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        songsSearch.setText("");
        titles = songs.getTitles();
        setupSongsList();
    }

    public void setupSongsList() {
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, titles);
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

