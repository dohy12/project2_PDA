package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Board extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;

    ArrayList<Board_Info> boardInfoList = new ArrayList<Board_Info>();

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

        Intent myIntent = getIntent();
        int notice = myIntent.getIntExtra("notice", 0);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        BoardCallable boardCallable = new BoardCallable(notice);
        Future<ArrayList<Board_Info>> future = executorService.submit(boardCallable);

        try{
            boardInfoList = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        showBoardList();

    }

    private void showBoardList(){

        for(int i=0;i<boardInfoList.size();i++) {
            final Board_Info boardInfo = boardInfoList.get(i);
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

    public void goBoardWriting(View view){
        LocalDateTime date = LocalDateTime.now();
        Board_Info fake = new Board_Info(0,false, "", "", date, 0, 0);
        Intent intent = new Intent(this, Board_Writing.class);
        intent.putExtra("selectedBoard", fake);
        startActivity(intent);
    }

    private class BoardCallable implements Callable<ArrayList<Board_Info>> {
        private int notice;

        public BoardCallable(int notice) {
            this.notice = notice;
        }

        public ArrayList<Board_Info> call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://10.0.2.2:8080/Community/";
            //서버에 api 빌드 후 경로 수정
            //comments_num 받아오는 부분이 아직 빌드 되지 않았음
            String GroupId = "deaa01013b0144e99faab90ecd670950/";

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

                for (int i = 0; i < jsonArray.length(); i++) {
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

                    boardInfoList.add(new Board_Info(BID, isNotice, title, name, date, views_num, comments_num, contents));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return boardInfoList;
        }
    }
}