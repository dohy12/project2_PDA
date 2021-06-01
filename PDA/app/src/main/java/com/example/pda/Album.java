package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class Album extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;

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

        showList();
    }

    private void showList(){
        for(int i=0;i<10;i++){
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
        }
    }
}