package com.example.musicplayer.Music;

import android.graphics.Bitmap;
import android.net.Uri;

public class Songs {
    private String title;//歌名
    private String album;//专辑
    private String artist;//歌手名称
    private Bitmap album_bitMap;//专辑封面
    private long id; // 歌曲ID
    private String album_Id;//专辑ID
    private String displayName; //显示名称
    private String duration; // 歌曲时长
    private long size; // 歌曲大小
    private Uri uri; // 歌曲路径
    private String lrc_title; // 歌词名称
    private String lrc_size; // 歌词大小
    private String path;

    public Songs(String title, String album, String artist, Bitmap album_bitMap, long id, String album_id, String displayName, String duration, long size, Uri uri, String lrc_title, String lrc_size,String path) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.album_bitMap = album_bitMap;
        this.id = id;
        album_Id = album_id;
        this.displayName = displayName;
        this.duration = duration;
        this.size = size;
        this.uri = uri;
        this.lrc_title = lrc_title;
        this.lrc_size = lrc_size;
        this.path=path;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }

    public Songs(){
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Bitmap getAlbum_bitMap() {
        return album_bitMap;
    }

    public void setAlbum_bitMap(Bitmap album_bitMap) {
        this.album_bitMap = album_bitMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbum_Id() {
        return album_Id;
    }

    public void setAlbum_Id(String album_Id) {
        this.album_Id = album_Id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getLrc_title() {
        return lrc_title;
    }

    public void setLrc_title(String lrc_title) {
        this.lrc_title = lrc_title;
    }

    public String getLrc_size() {
        return lrc_size;
    }

    public void setLrc_size(String lrc_size) {
        this.lrc_size = lrc_size;
    }
}
