package com.example.pda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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

    public void openMenu(View view){
        View anchor = findViewById(R.id.menu_anchor);
        final PopupMenu popupMenu = new PopupMenu(this, anchor);
        getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu1:
                        Toast.makeText(toolbar.getActivity(), "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu2:
                        Toast.makeText(toolbar.getActivity(), "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu3:
                        Toast.makeText(toolbar.getActivity(), "메뉴 3 클릭", Toast.LENGTH_SHORT).show();
                        break;
                }

                return false;
            }
        });
        popupMenu.show();
    }
}