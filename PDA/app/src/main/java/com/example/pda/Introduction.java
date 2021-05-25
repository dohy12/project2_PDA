package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Introduction extends AppCompatActivity {

    private LinearLayout image_container;
    private LayoutInflater inflater;
    ArrayList<Drawable> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        image_container = findViewById(R.id.board_image_container);

        imageList = new ArrayList<>();
        imageList.add(getResources().getDrawable(R.drawable.img1, null));
        imageList.add(getResources().getDrawable(R.drawable.img5, null));

        showImageList();
    }

    private void showImageList(){
        for(int i=0;i<imageList.size();i++) {
            View v = inflater.inflate(R.layout.board_image, null);
            image_container.addView(v);

            ((ImageView)v.findViewById(R.id.board_image)).setImageDrawable(imageList.get(i));
        }
    }

    public void goModifying(View view){
        Intent intent = new Intent(this, IntroductionModifying.class);
        startActivity(intent);
    }



}