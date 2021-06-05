package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

public class Album extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;
    private ScrollView scrollView;

    private int maxCount = 10;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        container = (LinearLayout)findViewById(R.id.container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        scrollView = findViewById(R.id.scrollView);

        showList();

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldx, int oldy) {
                View v = scrollView.getChildAt(0);
                int diff = (v.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));

                if (diff == 0) { // 스크롤 bottom
                    if (maxCount>0)
                        showList();
                }
            }
        });

    }

    private void showList(){
        maxCount--;
        for(int i=0;i<4;i++){
            View v = inflater.inflate(R.layout.album_layout, null);
            container.addView(v);

            v.findViewById(R.id.album).setClipToOutline(true);
            v.findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AlbumContent.class);
                    startActivity(intent);
                }
            });

            ImageView imageView = v.findViewById(R.id.image);
            String imageUrl = "https://crabox.io/test/dohy/images/back1.jpg";
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }

    public void goAlbumAdd(View view){
        Intent intent = new Intent(this, AlbumAdd.class);
        startActivity(intent);
    }
}