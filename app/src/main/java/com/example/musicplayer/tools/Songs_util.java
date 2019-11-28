package com.example.musicplayer.tools;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.example.musicplayer.LocalMusicActivity;
import com.example.musicplayer.Music.Songs;
import com.example.musicplayer.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Songs_util {
    Context mcontext ;
    static String path;
    public List<Songs> getSongListFromPhone(Context context) throws IOException{
        List<Songs> list = new ArrayList<Songs>();
        mcontext = context;
        try {


            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
            if (cursor.getCount() > 0) {
                Log.d("测试", "" + cursor.getCount());
                while (cursor.moveToNext()) {
                    Songs songs = new Songs();
                    songs.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
//                Log.d("AUG",""+songs.getTitle());
                    songs.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
//                    Log.d("测试", "" + songs.getArtist());
                    songs.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
//                    Log.d("测试", "" + songs.getAlbum());
                    songs.setAlbum_Id(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
//                Log.d("测试",""+songs.getTitle());
                    songs.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                    songs.setDuration(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                    songs.setUri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build());
                    Log.d("TAG", "" + songs.getUri());
                    songs.setDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
//                songs.setId(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)));
                    songs.setSize(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))));
//                songs.setLrc_title(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.)));
                    songs.setAlbum_bitMap(getBitmapFromUri(songs.getUri()));
                    if (songs.getSize() > 1000 * 800) {
                        if (songs.getTitle().contains("-")) {
                            String[] str = songs.getTitle().split("-");
                            songs.setTitle(str[1]);
                            songs.setArtist(str[0]);
                        }
                        list.add(songs);
                    }
                }
                cursor.close();
            } else {
                Log.d("测试", "本地音乐为空");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 定义一个方法用来格式化获取到的时间
     */
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            return time / 1000 / 60 + ":0" + time / 1000 % 60;
        } else {
            return time / 1000 / 60 + ":" + time / 1000 % 60;
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException{
     ParcelFileDescriptor parcelFileDescriptor = mcontext.getContentResolver().openFileDescriptor(uri, "r");
    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
     parcelFileDescriptor.close();
    return image;

    }







//    private void getImage(int id)
//    {
//        int album_id = id;
//        String albumArt = getAlbumArt(album_id);
//        Bitmap bm = null;
//        if (albumArt == null)
//        {
//            mImageView.setBackgroundResource(R.drawable.noalbum);
//        }
//else 
//        { 
//            bm = BitmapFactory.decodeFile(albumArt); 
//            BitmapDrawable bmpDraw = new BitmapDrawable(bm); 
//            ((ImageView) mImageView).setImageDrawable(bmpDraw); 
//        } 
//    }
//
//    private String getAlbumArt(int album_id) 
//
//    {  
//        String mUriAlbums = "content://media/external/audio/albums";  
//        String[] projection = new String[] { "album_art" };  
//        Cursor cur = this.getContentResolver().query(  Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),  projection, null, null, null); 
//        String album_art = null; 
//        if (cur.getCount() > 0 && cur.getColumnCount() > 0) 
//        {  cur.moveToNext(); 
//            album_art = cur.getString(0); 
//        } 
//        cur.close(); 
//        cur = null; 
//        return album_art; 
//    }



}
