package com.example.musicplayer.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicplayer.Music.Songs;
/*
not necessary
 */

public class ChangeMyList {
    SQLiteDatabase db;
    private void changeMyList(Context context){
        myList hp = new myList(context,"MyList",null,1);
        db =hp.getReadableDatabase() ;
    }
    public boolean addToMyList(Songs songs){
        ContentValues values = new ContentValues();
        values.put("title",songs.getTitle());
        values.put("artist",songs.getArtist());
        values.put("disPlayname",songs.getDisplayName());
        db.insert("myList",null,values);
        return true;
    }
    public Songs searchFromMyList(String title,String artist){
        Cursor cursor = db.rawQuery("select * from myList where title=? and artist=?",new String[]{title,artist});
        Songs songs = new Songs();

        while(cursor.moveToNext()) {

            songs.setTitle(cursor.getString(cursor.getColumnIndex(title)));
            songs.setArtist(cursor.getString(cursor.getColumnIndex(artist)));
            songs.setDisplayName(cursor.getString(cursor.getColumnIndex("disPlayname")));
        }
        cursor.close();
        return songs;
    }

}
