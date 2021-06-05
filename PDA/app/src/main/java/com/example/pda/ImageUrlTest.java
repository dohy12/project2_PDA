package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUrlTest extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_url_test);

        imageView = findViewById(R.id.image);

        String imageUrl = "https://crabox.io/test/dohy/spr_monsterball.png";
        Glide.with(this).load(imageUrl).into(imageView);
    }
}