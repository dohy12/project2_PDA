package com.example.pda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlbumContent extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ImageViewPagerAdapter pagerAdapter;

    private TextView albumTitle;
    private LinearLayout albumPageCheckContainer;

    private AlbumCheckPage pageChecker;

    private ArrayList<String> imageUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_content);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        imageUrlList = new ArrayList<>();
        imageUrlList.add("https://crabox.io/test/dohy/images/back1.jpg");
        imageUrlList.add("https://crabox.io/test/dohy/images/back2.jpg");
        imageUrlList.add("https://crabox.io/test/dohy/images/back3.jpg");
        imageUrlList.add("https://crabox.io/test/dohy/images/back4.png");

        pageChecker = new AlbumCheckPage(this, (LinearLayout) findViewById(R.id.albumContent_checkPage), imageUrlList.size());

        albumTitle = findViewById(R.id.album_title);
        albumPageCheckContainer = findViewById(R.id.albumContent_checkPage);

        viewPager = (ViewPager)findViewById(R.id.container);
        pagerAdapter = new ImageViewPagerAdapter(this, imageUrlList);
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

    public void openMenu(View view){
        View anchor = findViewById(R.id.menu_anchor);
        final PopupMenu popupMenu = new PopupMenu(this, anchor);
        getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();

        for(int i=0; i<5;i++)
            menu.add(Menu.NONE, i, Menu.NONE, "메뉴"+i);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i= menuItem.getItemId();

                Toast.makeText(toolbar.getActivity(), i + "선택", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.show();
    }
}