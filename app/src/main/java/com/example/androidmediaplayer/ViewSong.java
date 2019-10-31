package com.example.androidmediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewSong extends AppCompatActivity {
    EditText title;
    EditText url;
    Button back;
    Intent intent;
    SongsDB songsDB;
    String old_title;
    MediaPlayer mediaPlayer;
    int playbackPosition;

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
        playbackPosition = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
        playbackPosition = 0;
    }

    public void play(View v) {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(playbackPosition);
                mediaPlayer.start();
            } else if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            } else {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(url.getText().toString());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "That mp3 url does not exist!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    mediaPlayer = null;
                    killMediaPlayer();
                    playbackPosition = 0;
                }
            }
    }

    public void pause(View v) {
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            playbackPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    public void stop(View v) {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            killMediaPlayer();
            playbackPosition = 0;
        }
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

    private void killMediaPlayer() {
        if(mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
