package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.Adapter.MusicListAdapter;
import com.example.musicplayer.Music.Songs;
import com.example.musicplayer.tools.Songs_util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LocalMusicActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private List<Songs> songsList = new ArrayList<Songs>();
    private MediaPlayer mediaPlayer= new MediaPlayer();

    private ImageView music_play_button;
    private ImageView music_stop_button;
    private ImageView music_next_button;
    private ImageView music_last_button;
    private TextView music_name_textView;
    private SeekBar music_seekBar ;
    private TextView now_time;

    private ImageView play_in_rotation;
    private ImageView play_in_sequence;
    private ImageView single_rotation;
    private ImageView random_play;

    private int current_music;

    private boolean isChanging=false,flag=false;
    private Thread thread;
    private  int current_position;
    public int ImageId;

    public void seekBar_init(){
    music_play_button = findViewById(R.id.play_music);
    music_stop_button = findViewById(R.id.stop_music);
    music_next_button= findViewById(R.id.next_music);
    music_last_button = findViewById(R.id.last_music);
    music_name_textView = findViewById(R.id.text_music_name);
    music_seekBar = findViewById(R.id.music_seek_bar);
    now_time = findViewById(R.id.now_time);

    play_in_rotation=findViewById(R.id.play_in_rotation);
    play_in_sequence=findViewById(R.id.play_in_sequence);
    single_rotation = findViewById(R.id.single_music_rotation);
    random_play= findViewById(R.id.random_play);


    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);

        seekBar_init();//初始化seekbar；
        music_play_button.setOnClickListener(new ClickEvent());
        music_stop_button.setOnClickListener(new ClickEvent());
        music_next_button.setOnClickListener(new ClickEvent());
        music_last_button.setOnClickListener(new ClickEvent());
        play_in_sequence.setOnClickListener(new ClickEvent());
        play_in_rotation.setOnClickListener(new ClickEvent());
        single_rotation.setOnClickListener(new ClickEvent());
        random_play.setOnClickListener(new ClickEvent());
        music_seekBar.setOnSeekBarChangeListener(this);

        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }//初始书音乐列表
        MusicListAdapter  musicListAdapter = new MusicListAdapter(LocalMusicActivity.this,R.layout.songs_list,songsList);
        ListView listView = findViewById(R.id.songs_list_view);
        listView.setAdapter(musicListAdapter);


        verifyStoragePermissions(LocalMusicActivity.this);//初始化权限
        /*
        列表点击事件
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                String dataSource = songsList.get(position).getUri();
                Log.d("TAG", "" + songsList.get(position).getTitle());
                    current_music = position;
                initMediaPlayer(LocalMusicActivity.this, songsList.get(position));

                    now_time.setText(""+ShowTime(mediaPlayer.getDuration()));
                    music_name_textView.setText(songsList.get(position).getTitle());
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                thread = new Thread(new SeekBarThread());
                thread.start();


               /* if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else{
                    mediaPlayer.start();
                }*/

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                //添加至我的歌单;
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    //初始化音乐列表
    public void init() throws IOException{
        try {
            Songs_util songs_util = new Songs_util();
            songsList = songs_util.getSongListFromPhone(this);
            Log.d("TAg","songlist长度为"+songsList.size());
            Log.d("TAG",songsList.get(1).getDisplayName());
            MusicListAdapter musicListAdapter = new MusicListAdapter(LocalMusicActivity.this, R.layout.songs_list, songsList);
            ListView listView = findViewById(R.id.songs_list_view);
            listView.setAdapter(musicListAdapter);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.scan_local_music:
                //扫描本地音乐文件
                    try {
                        verifyStoragePermissions(LocalMusicActivity.this);
                        init();
                    } catch (IOException e) {
                        e.printStackTrace();

                }

                break;
            case R.id.select_sort_way:
                //选择排序方式
                break;
                default:
                    break;
        }
        return true;
    }


    public static void verifyStoragePermissions(Activity activity){
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }

    }
    public void initMediaPlayer(Context context, Songs songs){

        try {
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(songs.getPath());
                mediaPlayer.prepare();
                music_seekBar.setMax(mediaPlayer.getDuration());

            }else {

                Toast.makeText(context,"请插入sd卡",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();

        }

    }
    protected void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();//停止音频的播放
        }
        mediaPlayer.release();//释放资源
        super.onDestroy();
    }

    class  ClickEvent implements View.OnClickListener{

        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.play_music:
                    if(mediaPlayer.isPlaying()){
                        current_position=mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
                        music_play_button.setVisibility(View.GONE);
                        music_stop_button.setVisibility(View.VISIBLE);
                        flag=true;
                        thread = new Thread(new SeekBarThread());
                        thread.start();
                    }
                    else if(flag){
                       mediaPlayer.start();
                       mediaPlayer.seekTo(current_position);
                       music_play_button.setVisibility(View.VISIBLE);
                       music_stop_button.setVisibility(View.GONE);
                       flag=false;
                        thread = new Thread(new SeekBarThread());
                        thread.start();
                    }
                    else{
                        music_seekBar.setMax(mediaPlayer.getDuration());
                        try{

                        }catch (IllegalStateException e){
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                        music_play_button.setVisibility(View.VISIBLE);
                        thread = new Thread(new SeekBarThread());
                        thread.start();
                    }
                    thread = new Thread(new SeekBarThread());
                    thread.start();
                    break;
                case R.id.stop_music:
                    mediaPlayer.start();
                    music_play_button.setVisibility(View.VISIBLE);
                    music_stop_button.setVisibility(View.GONE);
                    music_seekBar.setProgress(current_position);
                    thread = new Thread(new SeekBarThread());
                    thread.start();
                    break;
                case R.id.next_music:
                        //下一首歌
                    next();
                     break;
                case R.id.last_music:
                    //上一首歌

                     previous();
                    break;
                case R.id.play_in_sequence:
                    ImageId=0;
                    play_in_rotation.setVisibility(View.VISIBLE);
                    single_rotation.setVisibility(View.GONE);
                    play_in_sequence.setVisibility(View.GONE);
                    random_play.setVisibility(View.GONE);
                    Toast.makeText(LocalMusicActivity.this,"列表循环",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.play_in_rotation:
                    ImageId=1;
                    play_in_sequence.setVisibility(View.GONE);
                    play_in_rotation.setVisibility(View.GONE);
                    random_play.setVisibility(View.GONE);
                    single_rotation.setVisibility(View.VISIBLE);
                    Toast.makeText(LocalMusicActivity.this,"单曲循环",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.single_music_rotation:
                    ImageId=2;
                    single_rotation.setVisibility(View.GONE);
                    play_in_sequence.setVisibility(View.GONE);
                    play_in_rotation.setVisibility(View.GONE);
                    random_play.setVisibility(View.VISIBLE);
                    Toast.makeText(LocalMusicActivity.this,"随机播放",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.random_play:
                    ImageId=3;
                    single_rotation.setVisibility(View.GONE);
                    play_in_sequence.setVisibility(View.VISIBLE);
                    play_in_rotation.setVisibility(View.GONE);
                    random_play.setVisibility(View.GONE);
                    Toast.makeText(LocalMusicActivity.this,"顺序播放",Toast.LENGTH_SHORT).show();
                    break;
                    default:
                        break;

            }
        }
    }
public void previous(){
    if(ImageId==0){
        desequence();
    }
    else if(ImageId==1){
        current_music=current_music;
    }
    else if(ImageId==2){
        Random();
    }
    else{
        if(current_music<=songsList.size()&&current_music>0){
            current_music--;
        }
        else{
            Toast.makeText(LocalMusicActivity.this,"已经是第一首了",Toast.LENGTH_SHORT).show();
        }
    }
    mediaPlayer.stop();
    mediaPlayer.reset();
    initMediaPlayer(LocalMusicActivity.this, songsList.get(current_music));
    mediaPlayer.start();
    music_name_textView.setText(songsList.get(current_music).getTitle());
    music_seekBar.setMax(mediaPlayer.getDuration());

    thread = new Thread(new SeekBarThread());
    thread.start();

}
    public void next(){
        if(ImageId==0){
            sequence();
        }
        else if(ImageId==1){
            current_music=current_music;
        }
        else if(ImageId==2){
            Random();
        }
        else{
            if(current_music<songsList.size()-1){
                current_music++;
            }
            else{
                Toast.makeText(LocalMusicActivity.this,"已经是最后一首了",Toast.LENGTH_SHORT).show();
            }
        }
            mediaPlayer.stop();
            mediaPlayer.reset();
            initMediaPlayer(LocalMusicActivity.this, songsList.get(current_music));
            mediaPlayer.start();
             music_name_textView.setText(songsList.get(current_music).getTitle());
            music_seekBar.setMax(mediaPlayer.getDuration());

            thread = new Thread(new SeekBarThread());
            thread.start();

    }
    private void sequence() {
        current_music++;
        if (current_music >= songsList.size()) {
            current_music = 0;
        }

    }
    private void desequence() {
        current_music--;
        if (current_music <0) {
            current_music = 0;
        }

    }
  private void Random(){
        current_music=new Random().nextInt(songsList.size());
  }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b){
      now_time.setText(""+ShowTime(i));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar){
        isChanging=true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar){
    now_time.setText(""+ShowTime(mediaPlayer.getDuration()));
    mediaPlayer.seekTo(seekBar.getProgress());
    isChanging=false;
    thread = new Thread(new SeekBarThread());
    thread.start();
    }
    public String ShowTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }


    class SeekBarThread implements Runnable {
        @Override
        public void run() {
            while (!isChanging && mediaPlayer.isPlaying()) {
                // 将SeekBar位置设置到当前播放位置
               music_seekBar.setProgress(mediaPlayer.getCurrentPosition());
                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    //播放进度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
