package com.example.pda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlbumContent extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ImageViewPagerAdapter pagerAdapter;

    private TextView albumTitle;
    private LinearLayout albumPageCheckContainer;

    private AlbumCheckPage pageChecker;

    private int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_content);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        pageChecker = new AlbumCheckPage(this, (LinearLayout) findViewById(R.id.albumContent_checkPage), count);

        albumTitle = findViewById(R.id.album_title);
        albumPageCheckContainer = findViewById(R.id.albumContent_checkPage);

        viewPager = (ViewPager)findViewById(R.id.container);
        pagerAdapter = new ImageViewPagerAdapter(this, count);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

            }

            @Override
            public void onPageSelected(int position) {
                albumTitle.setText("사진" + position);
                pageChecker.setPageChecker(position);
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

    }

    public void goShowReply(View view){
        Intent intent = new Intent(this, ShowReply.class);
        startActivity(intent);
    }
}