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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AlbumContent extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ImageViewPagerAdapter pagerAdapter;

    private TextView albumTitle;
    private LinearLayout albumPageCheckContainer;

    private AlbumCheckPage pageChecker;

    private ArrayList<String> imageUrlList;

    private int PID;

    private String title;
    private String location;
    private String intro;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_content);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        PID = getIntent().getIntExtra("PID", 0);
        title = getIntent().getStringExtra("Title");
        location = getIntent().getStringExtra("Loc");
        intro = getIntent().getStringExtra("Intro");
        date = getIntent().getStringExtra("Date");

        imageUrlList = new ArrayList<>();
        getImageUrlList();
    }

    private void getImageUrlList(){
        String JWT;
        JWT = app.getJWT();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String Host = "http://"+ app.getHostip() + ":";
        String port = "8080";
        String AccessPath = "/album/images/";
        String url = Host + port + AccessPath + app.getGroupId() + "/" + PID;
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
                    String imagesText = response.body().string();

                    JsonParser jsonParser = new JsonParser();
                    JsonArray jsonArray = (JsonArray) jsonParser.parse(imagesText);

                    for(int i=0;i<jsonArray.size(); i++){

                        String imgUrl = "http://"+ app.getHostip() +":8080/images/" + jsonArray.get(i).toString().substring(1,jsonArray.get(i).toString().length()-1);
                        imageUrlList.add(imgUrl);

                        System.out.println(imgUrl);
                    }
                    setPageChecker();

//                    Log.d("TAG", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).run();


    }


    private void setPageChecker(){
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
                albumTitle.setText("사진" + (position+1));
                pageChecker.setPageChecker(position);
            }

            @Override
            public void onPageScrollStateChanged(int state){

            }
        });

    }


    public void openMenu(View view){
        View anchor = findViewById(R.id.menu_anchor);
        final PopupMenu popupMenu = new PopupMenu(this, anchor);
        getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();

        menu.add(Menu.NONE, 0, Menu.NONE, "상세 정보");
        menu.add(Menu.NONE, 1, Menu.NONE, "앨범 삭제");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i= menuItem.getItemId();

                switch (i){
                    case 0:
                        showAlbumDetail();
                        break;

                    case 1:
                        deleteAlbum();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showAlbumDetail(){
        AlbumInfoFragment fragment = new AlbumInfoFragment(title, location, intro, date);

        fragment.show(getSupportFragmentManager(),"tag");

    }


    private void deleteAlbum(){
        String JWT;
        JWT = app.getJWT();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        String Host = "http://" + app.getHostip() + ":";
        String port = "8080";
        String AccessPath = "/album/";
        String url = Host + port + AccessPath + app.getGroupId() + "/" + PID;
        System.out.println(url);


        final Request request = new Request.Builder()
                .url(url)
                .delete()
                .addHeader("JWT", JWT)
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();

                    ((Album)Album.mContext).refresh();

                    Intent intent = new Intent(AlbumContent.this, Album.class);
                    startActivity(intent);

                    finish();

//                    Log.d("TAG", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).run();
    }
}