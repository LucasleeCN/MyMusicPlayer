package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.musicplayer.Adapter.MusicListAdapter;
import com.example.musicplayer.Fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView local_music;
    private  TextView recent_play;
    private TextView download_manager;
    private TextView my_radio;
    private TextView my_collections;
    private RelativeLayout layout_local ;
    private  RelativeLayout layout_recent;
    private  RelativeLayout layout_download;
    private RelativeLayout layout_radio;
    private RelativeLayout layout_collections;
    private TextView textView_p1;
    private TextView textView_p2;
    private TextView textView_p3;
    private TextView textView_p4;
    private TextView textView_p5;

//    private TextView my_music_textView;
//    private TextView online_music_textView;
//    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public void init(){
        textView1= findViewById(R.id.music_logo);
        textView2 =findViewById(R.id.recent_play_logo);
        textView3 = findViewById(R.id.download_manager_logo);
        textView4 = findViewById(R.id.my_radio_logo);
        textView5 = findViewById(R.id.my_collections_logo);
        textView6 = findViewById(R.id.my_menu_logo);
        textView_p1= findViewById(R.id.textView_p1);
        textView_p2= findViewById(R.id.textView_p2);
        textView_p3= findViewById(R.id.textView_p3);
        textView_p4= findViewById(R.id.textView_p4);
        textView_p5= findViewById(R.id.textView_p5);


        local_music = findViewById(R.id.local_music);
        recent_play = findViewById(R.id.recent_play);
        download_manager =findViewById(R.id.download_manager);
        my_radio = findViewById(R.id.my_radio);
        my_collections = findViewById(R.id.my_collections);
        layout_local = findViewById(R.id.RelativeLayout_local);
        layout_recent = findViewById(R.id.RelativeLayout_recent);
        layout_download = findViewById(R.id.RelativeLayout_download);
        layout_radio = findViewById(R.id.RelativeLayout_radio);
        layout_collections = findViewById(R.id.RelativeLayout_collections);

//        my_music_textView= findViewById(R.id.main_my_music_textView);
//        online_music_textView = findViewById(R.id.main_my_music_textView);
//        viewPager = findViewById()
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Typeface ty = Typeface.createFromAsset(getAssets(),"font/iconfont.ttf");
        textView2.setTypeface(ty);
        textView1.setTypeface(ty);
        textView3.setTypeface(ty);
        textView4.setTypeface(ty);
        textView5.setTypeface(ty);
        textView6.setTypeface(ty);
        textView_p1.setTypeface(ty);
        textView_p2.setTypeface(ty);
        textView_p3.setTypeface(ty);
        textView_p4.setTypeface(ty);
        textView_p5.setTypeface(ty);

        local_music.setOnClickListener(this);
        recent_play.setOnClickListener(this);
        download_manager.setOnClickListener(this);
        my_radio.setOnClickListener(this);
        my_collections.setOnClickListener(this);

        layout_local.setOnClickListener(this);
        layout_recent.setOnClickListener(this);
        layout_download.setOnClickListener(this);
        layout_radio.setOnClickListener(this);
        layout_collections.setOnClickListener(this);

//        my_music_textView.setOnClickListener(this);
//        online_music_textView.setOnClickListener(this);
//        MusicListAdapter musicListAdapter = new MusicListAdapter();
//        MyFragment myFragment = new MyFragment();
//        fragmentList.add(myFragment);
//        viewPager.setAdapter();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.local_music:case R.id.RelativeLayout_local:
                Intent intent1 = new Intent(MainActivity.this,LocalMusicActivity.class);
                startActivity(intent1);
                break;
            case R.id.recent_play: case R.id.RelativeLayout_recent:
                Intent intent2 = new Intent(MainActivity.this,RecentPlayActivity.class);
                startActivity(intent2);
                break;
            case R.id.download_manager: case R.id.RelativeLayout_download:
                Intent intent3 = new Intent(MainActivity.this,DownloadActivity.class);
                startActivity(intent3);
                break;
            case R.id.my_radio: case R.id.RelativeLayout_radio:
                Intent intent4 = new Intent(MainActivity.this,MyRadioActivity.class);
                startActivity(intent4);
                break;
            case R.id.my_collections: case R.id.RelativeLayout_collections:
                Intent intent5 = new Intent(MainActivity.this,MyCollectionsActivity.class);
                startActivity(intent5);
                break;

            default:
               break;
        }
    }
}
