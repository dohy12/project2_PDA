package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pda.entity.Guestbook;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Mypage extends AppCompatActivity {
    private boolean IsEnable = false;
    private LinearLayout container;
    private LayoutInflater inflater;
    Member mem;
    ArrayList<GuestBook> guestBookList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        container = (LinearLayout) findViewById(R.id.guestBook_container);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        guestBookList = new ArrayList<>();

        if (IsEnable) {
            try {
                getGuestBook();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            guestBookList.add(new GuestBook("이도희", "안녕하세요", LocalDateTime.of(2021, 5, 15, 9, 51)));
            guestBookList.add(new GuestBook("이도희2", "안녕하세요", LocalDateTime.of(2021, 5, 16, 9, 51)));
            guestBookList.add(new GuestBook("이도희3", "안녕하세요", LocalDateTime.of(2021, 5, 17, 9, 51)));
            guestBookList.add(new GuestBook("이도희4", "안녕하세요", LocalDateTime.of(2021, 5, 18, 9, 51)));

            mem = new Member(1, "이도희1", 27, "010-2890-6812", "dohy12@naver.com", "경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다.");
            setMypage(mem);
            showBookList();
        }
    }

    private void setMypage(Member mem) {
        findViewById(R.id.mypage_profileImage).setClipToOutline(true);

        String str_name_age = mem.getName() + "(" + mem.getAge() + ")";
        ((TextView) findViewById(R.id.memList_name)).setText(str_name_age);

        String str_phone = "Mobile. " + mem.getPhone();
        ((TextView) findViewById(R.id.memList_phone)).setText(str_phone);

        String str_email = "E-mail. " + mem.getEmail();
        ((TextView) findViewById(R.id.memList_email)).setText(str_email);
        ((TextView) findViewById(R.id.memList_intro)).setText(mem.getIntro());
    }

    private void showBookList() {
        ((TextView) findViewById(R.id.guestBook_num)).setText("방명록(" + guestBookList.size() + "개)");
        for (int i = 0; i < guestBookList.size(); i++) {
            GuestBook guestBook = guestBookList.get(i);
            View v = inflater.inflate(R.layout.guestbook, null);
            container.addView(v);

            v.findViewById(R.id.profile_image).setClipToOutline(true);

            ((TextView) v.findViewById(R.id.guestBook_name)).setText(guestBook.getName());
            ((TextView) v.findViewById(R.id.guestBook_comment)).setText(guestBook.getComment());

            String formatDate = guestBook.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            ((TextView) v.findViewById(R.id.guestBook_date)).setText(formatDate);
        }
    }

    private void getGuestBook() throws InterruptedException {
        final String JWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTAzMjM4NTQ4IiwiZXhwIjoxNjIyNzA2MDgxLCJpYXQiOjE2MjI3MDQyODF9.hbiryzoxngs-GVQpOniXYmgavzMQEvk8AA-FSndhkWK7YnS_wqwTZk1E2G0wBessg_8ZadQSym-Aocdo1om_8Q";
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @NotNull
//                    @Override
//                    public Response intercept(@NotNull Chain chain) throws IOException {
//                        Request newRequest = chain.request()
//                                .newBuilder()
//                                .addHeader("JWT", JWT).build();
//                        return chain.proceed(newRequest);
//                    }
//                })
                .build();
        String Host = "http://10.0.2.2:";
        String port = "8080";
        String AccessPath = "/GuestBook/ReceiveMessage";
        String url = Host + port + AccessPath;
        System.out.println(url);
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT",JWT)
                .build();
        final Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("TAG", "onFailure: ");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("TAG", "onResponse: " + response.body().string());
//                System.out.println("执行");
//                Gson gson = new Gson();
//                Guestbook[] guestbook = gson.fromJson(response.body().string(), Guestbook[].class);
//                for (Guestbook temp : guestbook) {
//                    System.out.println(temp);
//                    Date date = temp.getTime();
//                    Instant instant = date.toInstant();
//                    ZoneId zoneId = ZoneId.systemDefault();
//                    LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
//                    guestBookList.add(new GuestBook(temp.getSenderName(), temp.getContent(), localDateTime));
//                }
//            }
//        });
        Runnable networkTask = new Runnable() {
//        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("执行");
                    Response response = call.execute();
                    Gson gson = new Gson();
                    Guestbook[] guestbook = gson.fromJson(response.body().string(), Guestbook[].class);
                    for (Guestbook temp : guestbook) {
                        System.out.println(temp);
                        Date date = temp.getTime();
                        Instant instant = date.toInstant();
                        ZoneId zoneId = ZoneId.systemDefault();
                        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                        guestBookList.add(new GuestBook(temp.getSenderName(), temp.getContent(), localDateTime));
                    }
//                    Log.d("TAG", "run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).run();
        System.out.println("执行完毕");
        mem = new Member(1, "이도희1", 27, "010-2890-6812", "dohy12@naver.com", "경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다.");
        setMypage(mem);
        showBookList();
    }


}