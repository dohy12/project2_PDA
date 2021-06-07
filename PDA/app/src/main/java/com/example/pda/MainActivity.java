package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    Drawer drawer;
    Toolbar toolbar;

    ViewPager mainViewPager;

    Album_info album;

    LinearLayout album_Container;
    LinearLayout board1_Container;
    LinearLayout board2_Container;

    LayoutInflater inflater;

    ArrayList<Board_Info> boardInfoList1 = new ArrayList<Board_Info>();
    ArrayList<Board_Info> boardInfoList2 = new ArrayList<Board_Info>();

    private List<String> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        ///툴바 세팅/////////////
        drawer = new Drawer(findViewById(R.id.drawerBar), this);
        toolbar = new Toolbar(findViewById(R.id.toolbar), drawer, 1, this);
        ////////////////////////

        album_Container = findViewById(R.id.main_album_container);
        board1_Container = findViewById(R.id.board1_container);
        board2_Container = findViewById(R.id.board2_container);

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        showMainImage();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        BoardCallable boardCallable = new BoardCallable(1);
        Future<ArrayList<Board_Info>> future = executorService.submit(boardCallable);

        try{
            boardInfoList1 = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showBoardList(boardInfoList1, board1_Container);


        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        BoardCallable boardCallable2 = new BoardCallable(0);
        Future<ArrayList<Board_Info>> future2 = executorService2.submit(boardCallable2);

        try{
            boardInfoList2 = future2.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showBoardList(boardInfoList2, board2_Container);

        getAlbumListFromServer();
    }

    public void goMemList(View view){
        Intent intent = new Intent(this, memberList.class);
        startActivity(intent);
    }

    public void goBoard1(View view){
        Intent intent = new Intent(this, Board.class);
        intent.putExtra("notice", 1);
        startActivity(intent);
    }

    private class BoardCallable implements Callable<ArrayList<Board_Info>> {
        private int notice;

        public BoardCallable(int notice) {
            this.notice = notice;
        }

        public ArrayList<Board_Info> call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://"+ app.getHostip() +":8080/Community/";
            //서버에 api 빌드 후 경로 수정
            //comments_num 받아오는 부분이 아직 빌드 되지 않았음
            String GroupId = app.getGroupId() + "/";

            String httpUrl = url + GroupId + notice;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            try {
                Response response = client.newCall(request).execute();

                JSONArray jsonArray = new JSONArray(response.body().string());

                int start;
                if (notice == 1)
                    start = 0;
                else
                    start = 5;
                for (int i = start; i < start+5; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int BID = jsonObject.getInt("B_ID");
                    boolean isNotice = jsonObject.getInt("isNotice") == 1;
                    String title = jsonObject.getString("title");
                    int UID = jsonObject.getInt("U_ID");
                    String name = jsonObject.getString("name");
                    String tempDate = jsonObject.getString("dateTime");
                    LocalDateTime date;
                    date = LocalDateTime.parse(tempDate, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    String contents = jsonObject.getString("contents");
                    int views_num = jsonObject.getInt("views_num");
                    int comments_num = jsonObject.getInt("comments_num");

                    if(notice == 1)
                        boardInfoList1.add(new Board_Info(BID, isNotice, title, name, date, views_num, comments_num, contents,UID));
                    else
                        boardInfoList2.add(new Board_Info(BID, isNotice, title, name, date, views_num, comments_num, contents,UID));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(notice == 1)
                return boardInfoList1;
            else
                return boardInfoList2;
        }
    }

    public void goBoard2(View view){
        Intent intent = new Intent(this, Board.class);
        intent.putExtra("notice", 0);
        startActivity(intent);
    }

    public void goIntroduction(View view){
        Intent intent = new Intent(this, Introduction.class);
        startActivity(intent);
    }

    public void goMembershipFee(View view){
        Intent intent = new Intent(this, MembershipFee.class);
        startActivity(intent);
    }

    public void goAlbum(View view){
        Intent intent = new Intent(this, Album.class);
        startActivity(intent);
    }


    private void getAlbumListFromServer(){
        String JWT;
        JWT = app.getJWT();
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();


        String Host = "http://" + app.getHostip() + ":";
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
                        album = temp;
                        break;
                    }
//                    Log.d("TAG", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).run();

        showAlbum();
    }


    private void showAlbum(){

        final Album_info al = album;

        View v = inflater.inflate(R.layout.album_layout, null);
        album_Container.addView(v);

        ((TextView)v.findViewById(R.id.album_title)).setText(al.getTitle());

        String str_date = al.getDate();

        int yy = Integer.parseInt(str_date.substring(0,4));
        int mm = Integer.parseInt(str_date.substring(4,6));
        int dd = Integer.parseInt(str_date.substring(6,8));

        String show_date = String.format("%d-%02d-%02d",yy,mm,dd);
        ((TextView)v.findViewById(R.id.album_date)).setText(show_date);
        ((TextView)v.findViewById(R.id.album_count)).setText(Integer.toString(al.getImage_cnt()));

        v.findViewById(R.id.album).setClipToOutline(true);
        v.findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlbumContent.class);
                intent.putExtra("PID",al.getA_ID());
                intent.putExtra("Title",al.getTitle());
                intent.putExtra("Loc",al.getLocation());
                intent.putExtra("Intro",al.getAlbum_intro());
                intent.putExtra("Date",al.getDate());

                startActivity(intent);
            }
        });

        ImageView imageView = v.findViewById(R.id.image);

        String imageUrl = "http://" +  app.getHostip() + ":8080/images/" + al.getImage_src();
        System.out.println(imageUrl);
        Glide.with(this).load(imageUrl).into(imageView);


    }

    private void showBoardList(ArrayList<Board_Info> boardInfoList, LinearLayout container){

        for(int i=0;i<boardInfoList.size();i++) {
            final Board_Info boardInfo = boardInfoList.get(i);
            View v = inflater.inflate(R.layout.main_board_layout, null);
            container.addView(v);

            ((TextView)v.findViewById(R.id.title)).setText(boardInfo.getTitle());

            String formatDate = boardInfo.getDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            ((TextView)v.findViewById(R.id.date)).setText(formatDate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goBoardContent(view, boardInfo);
                }
            });

        }
    }

    public void goBoardContent(View view, Board_Info selectedBoard){
        Intent intent = new Intent(this, Board_content.class);
        intent.putExtra("selectedBoard", selectedBoard);
        startActivity(intent);
    }

    private class ImageSrcRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        public ImageSrcRunnable(RetrofitService service, String GroupId) {
            this.service = service;
            this.GroupId = GroupId;
        }
        public void run() {
            retrofit2.Call<List<String>> call = service.GetintroImage(GroupId);
            try {
                result = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMainImage(){
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

        ImageView mainImageView = findViewById(R.id.mainImg);
        Glide.with(this).load("http://18.206.18.154:8080/images/" + result.get(0)).into(mainImageView);

    }

}