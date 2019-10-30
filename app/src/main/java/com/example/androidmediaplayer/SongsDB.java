package com.example.androidmediaplayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SongsDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongsDB";
    private static final String TABLE_NAME = "SONGS";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_URL = "url";

    public SongsDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY,"+COL_TITLE+" TEXT,"+ COL_URL +" TEXT)";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addSong(SongClass song)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, song.getTitle());
        values.put(COL_URL, song.getUrl());

        db.insert(TABLE_NAME, null,values);
        db.close();
    }

    void removeSong(String oldTitle) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COL_TITLE + "=?", new String[] {oldTitle});
        db.close();
    }

    void updateSong(String oldTitle, String title, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_URL, url);
        db.update(TABLE_NAME, values, COL_TITLE + "=?", new String[] {oldTitle});
        db.close();
    }

    SongClass getSong(String title)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        SongClass song = new SongClass();
        //Assume no duplicate titles
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_TITLE+" = ?",new String [] {title});
        c.moveToFirst();
        if(c.isNull(c.getColumnIndex(COL_TITLE)))
        {
            db.close();
            return null;
        }
        song.setTitle(c.getString(c.getColumnIndex(COL_TITLE)));
        song.setUrl(c.getString(c.getColumnIndex(COL_URL)));
        db.close();
        return song;
    }

    ArrayList<String> getTitles()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String temptTitle = "";
        ArrayList<String> titles = new ArrayList<>();
        String query = "SELECT "+ COL_TITLE +" FROM "+TABLE_NAME;
        //Assume no duplicate titles
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!(c.isAfterLast()))
        {
            temptTitle = c.getString(c.getColumnIndex(COL_TITLE));
            titles.add(temptTitle);
            c.moveToNext();
        }
        db.close();
        return titles;
    }
}
