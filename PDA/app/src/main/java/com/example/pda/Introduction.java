package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Introduction extends AppCompatActivity {

    private LinearLayout image_container;
    private LayoutInflater inflater;
    ArrayList<Drawable> imageList;
    private List<String> result;
    private String contents;
    private SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        image_container = findViewById(R.id.board_image_container);

        //.with(this).load(httpUrl.toString()).into((ImageView)v.find)
        imageList = new ArrayList<>();
        imageList.add(getResources().getDrawable(R.drawable.img1, null));
        //imageList.add(getResources().getDrawable(R.drawable.img5, null));

        showImageList();

        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                image_container.removeAllViews();
                /* swipe 시 진행할 동작 */
                showImageList();
                /* 업데이트가 끝났음을 알림 */
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showImageList(){
        String origin = "http://18.206.18.154:8080/";
        String GroupId = app.getGroupId();
        RetrofitService service1;

        //Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(origin)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service1 = retrofit.create(RetrofitService.class);

        Runnable r1 = new ImageSrcRunnable(service1, GroupId);
        Thread thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) {
        }

        r1 = new ContentsRunnable(service1, GroupId);
        thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) {
        }
        //System.out.println(contents);
        TextView textView = findViewById(R.id.board_container);
        textView.setText(contents);

        for(int i=0;i<result.size();i++) {
            View v = inflater.inflate(R.layout.board_image, null);
            image_container.addView(v);

            Glide.with(this).load("http://18.206.18.154:8080/images/" + result.get(i)).into((ImageView)v.findViewById(R.id.board_image));
            //((ImageView)v.findViewById(R.id.board_image)).setImageDrawable(imageList.get(i));
        }
    }

    private class ImageSrcRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        public ImageSrcRunnable(RetrofitService service, String GroupId) {
            this.service = service;
            this.GroupId = GroupId;
        }
        public void run() {
            Call<List<String>> call = service.GetintroImage(GroupId);
            try {
                result = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ContentsRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        public ContentsRunnable(RetrofitService service, String GroupId) {
            this.service = service;
            this.GroupId = GroupId;
        }
        public void run() {
            Call<String> call = service.GetintroContents(GroupId);
            try {
                contents = call.execute().body().toString();
                System.out.println("contents" + contents);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void goModifying(View view){
        Intent intent = new Intent(this, IntroductionModifying.class);
        startActivity(intent);
    }
}
