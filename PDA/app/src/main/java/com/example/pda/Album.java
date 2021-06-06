package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pda.entity.Guestbook;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Album extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;

    ArrayList<Album_info> album_infoList;

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

        album_infoList = new ArrayList<>();

        getAlbumListFromServer();
    }


    private void getAlbumListFromServer(){
        String JWT;
        JWT = app.getJWT();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();


        String Host = "http://" + "10.0.2.2" + ":";
        String port = "8080";
        String AccessPath = "/album/";
        String url = Host + port + AccessPath + app.getGroupId();
        System.out.println(url);


        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", JWT)
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String gsonText = Objects.requireNonNull(response.body()).string();
                    Gson gson = new Gson();
                    Album_info[] album_infos = gson.fromJson(gsonText, Album_info[].class);
                    for (Album_info temp : album_infos) {
                        album_infoList.add(temp);
                    }
//                    Log.d("TAG", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).run();

        showList();
    }

    private void showList(){
        for(int i=0;i<album_infoList.size();i++){
            Album_info al = album_infoList.get(i);

            View v = inflater.inflate(R.layout.album_layout, null);
            container.addView(v);

            ((TextView)v.findViewById(R.id.album_title)).setText(al.getTitle());
            ((TextView)v.findViewById(R.id.album_date)).setText(al.getDate());
            ((TextView)v.findViewById(R.id.album_count)).setText(Integer.toString(al.getImage_cnt()));
            ((TextView)v.findViewById(R.id.album_comment_count)).setText(Integer.toString(al.getComment_cnt()));

            v.findViewById(R.id.album).setClipToOutline(true);
            v.findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AlbumContent.class);
                    startActivity(intent);
                }
            });

            ImageView imageView = v.findViewById(R.id.image);


            String imageUrl = "https://crabox.io/test/dohy/images/back1.jpg"; //local테스트용ㅋㅋ
            Glide.with(this).load(imageUrl).into(imageView);

        }
    }

    public void goAlbumAdd(View view){
        Intent intent = new Intent(this, AlbumAdd.class);
        startActivity(intent);
    }
}