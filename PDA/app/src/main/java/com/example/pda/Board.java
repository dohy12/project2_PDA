package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Board extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;
    ArrayList<Board_Info> boardInfoList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        container = (LinearLayout)findViewById(R.id.board_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        OkHttpClient client = new OkHttpClient();

        //localhost 사용 시 @GET method 실행됨 확인
        //POST, DELETE, PUT 은 localhost 아니어도 실행됨 확인
/*
        String url = "http://10.0.2.2:8080/Community/";
        String GroupId = "deaa01013b0144e99faab90ecd670950/";
        //getGroupId 추가시 수정
        int isNotice = 1;

        String httpUrl = url + GroupId + isNotice;

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();

        System.out.println(httpUrl);
        System.out.println(request.headers().toString());
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("실패");
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println("성공");
                System.out.println(response.code());
                System.out.println(response.body().string());
            }
        });
*/

        boardInfoList = new ArrayList<>();
        boardInfoList.add(new Board_Info(1, true,"제목","이도희",   LocalDateTime.of(2021,5,22,10,30),222,5));
        boardInfoList.add(new Board_Info(2, false,"제목1","이도희1", LocalDateTime.of(2021,5,22,10,30),222,6));
        boardInfoList.add(new Board_Info(3, false,"제목2","이도희2", LocalDateTime.of(2021,5,22,10,30),222,7));
        boardInfoList.add(new Board_Info(4, false,"제목3","이도희3", LocalDateTime.of(2021,5,22,10,30),222,8));

        showBoardList();
    }

    private void showBoardList(){
        for(int i=0;i<boardInfoList.size();i++) {
            Board_Info boardInfo = boardInfoList.get(i);
            View v = inflater.inflate(R.layout.board, null);
            container.addView(v);

            ((TextView)v.findViewById(R.id.board_id)).setText(Integer.toString(boardInfo.getBoardId()));

            Boolean isNotice = boardInfo.getNotice();

            String str_notice = "";
            if(isNotice == true){
                ((TextView)v.findViewById(R.id.board_title)).setTextColor(Color.rgb(255,0,0));
                str_notice = "[공지] ";
            }

            ((TextView)v.findViewById(R.id.board_title)).setText(str_notice + boardInfo.getTitle());

            String formatDate = boardInfo.getDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
            String str_boardInfo = boardInfo.getName() + " " + formatDate + " 조회수 " + boardInfo.getViews_num();
            ((TextView)v.findViewById(R.id.board_info)).setText(str_boardInfo);
            ((TextView)v.findViewById(R.id.board_comments_num)).setText(Integer.toString(boardInfo.getComments_num()));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goBoardContent(view);
                }
            });

        }
    }


    public void goBoardContent(View view){
        Intent intent = new Intent(this, Board_content.class);
        startActivity(intent);
    }

    public void goBoardWriting(View view){
        Intent intent = new Intent(this, Board_Writing.class);
        startActivity(intent);
    }
}