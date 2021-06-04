package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    Drawer drawer;
    Toolbar toolbar;

    //goBoard()에 인자로 전달할 ArrayList<Board_Info>
    ArrayList<Board_Info> boardInfoList1 = new ArrayList<Board_Info>();
    ArrayList<Board_Info> boardInfoList2 = new ArrayList<Board_Info>();

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

        getBoards(1);
        getBoards(0);
    }

    public void goMemList(View view){
        Intent intent = new Intent(this, memberList.class);
        startActivity(intent);
    }

    public void goBoard1(View view){
        Intent intent = new Intent(this, Board.class);
        getBoards(1);
        intent.putExtra("boardInfoList", boardInfoList1);
        startActivity(intent);
        boardInfoList1.clear();
    }

    public void goBoard2(View view){
        Intent intent = new Intent(this, Board.class);
        getBoards(0);
        intent.putExtra("boardInfoList", boardInfoList2);
        startActivity(intent);
        boardInfoList2.clear();
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

    private void getBoards(final int notice){

        OkHttpClient client = new OkHttpClient();

        //localhost 사용 시 @GET method 실행됨 확인
        //POST, DELETE, PUT 은 localhost 아니어도 실행됨 확인
        String url = "http://10.0.2.2:8080/Community/";
        String GroupId = "deaa01013b0144e99faab90ecd670950/";
        //getGroupId 추가시 수정

        String httpUrl = url + GroupId + notice;

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
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObject = jsonArray.getJSONObject(i);
                        int BID = jObject.getInt("B_ID");
                        boolean isNotice = jObject.getInt("isNotice") == 1;
                        String title = jObject.getString("title");
                        String name = "temp";

                        //name과 date 관련 이슈 해결 후 수정

                        if(notice == 1)
                            boardInfoList1.add(new Board_Info(BID, isNotice, title, name, LocalDateTime.of(2021,5,22,10,30),222,5));
                        else
                            boardInfoList2.add(new Board_Info(BID, isNotice, title, name, LocalDateTime.of(2021,5,22,10,30),222,5));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("parsing failed");
                }
            }
        });

    }
}