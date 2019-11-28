package com.example.musicplayer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.musicplayer.LocalMusicActivity;
import com.example.musicplayer.Music.Songs;
import com.example.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends ArrayAdapter<Songs> {

    private  List<Songs> songs_list;

    public MusicListAdapter(Context context,int textViewResourceId,List<Songs> songs_list) {
        super(context,textViewResourceId,songs_list);

        this.songs_list=songs_list;
    }



    @Override
    public int getCount() {
        return songs_list.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View v;
        if(convertView == null){
            v= LayoutInflater.from(getContext()).inflate(R.layout.songs_list,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title=v.findViewById(R.id.songs_id_textView);
            viewHolder.album =v.findViewById(R.id.album_name_and_author);
            viewHolder.artist = v.findViewById(R.id.album_name_and_author);
            viewHolder.album_pic= v.findViewById(R.id.album_picture);
            v.setTag(viewHolder);
        }
        else {
            v= convertView;
            viewHolder = (ViewHolder) v.getTag();
        }
        Songs songs = songs_list.get(position);
        viewHolder.title.setText(songs.getTitle());
        viewHolder.album.setText(songs.getAlbum());
        viewHolder.artist.setText(songs.getArtist());
        if(songs.getAlbum_bitMap()!=null){
            viewHolder.album_pic.setImageBitmap(songs.getAlbum_bitMap());
        }else{
            viewHolder.album_pic.setImageResource(R.mipmap.album_default);
        }
        return v;

    }
}
