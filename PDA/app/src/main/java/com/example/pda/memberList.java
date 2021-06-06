package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class memberList extends AppCompatActivity {
    ArrayList<Member> memList;
    ArrayList<Member> memList_new;
    private LinearLayout container;
    private LinearLayout container_new;
    private LayoutInflater inflater;
    private AlertDialog.Builder alert;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        ((ImageView)findViewById(R.id.memberList_profileImage)).setImageBitmap(app.getProfile());
        findViewById(R.id.memberList_profileImage).setClipToOutline(true);

        container = (LinearLayout) findViewById(R.id.memberList_container);
        container_new = (LinearLayout) findViewById(R.id.memberList_container_new);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        alert = new AlertDialog.Builder(this);

        memList_new = new ArrayList<>();
//        memList_new.add(new Member(Integer.parseInt(app.getUid()), app.getName(), Integer.parseInt(app.getAge()), app.getPhone(), app.getEmail(), app.getIntro()));

        TextView textView = findViewById(R.id.memList_name);
        textView.setText(app.getName() + "(" + app.getAge().toString() + ")");
        TextView textView2 = findViewById(R.id.memList_phone);
//        System.out.println("app.getPhone() = " + app.getPhone());
        textView2.setText("mobile. "+app.getPhone());
        memList = new ArrayList<>();
        getMemberList();
        getWaitingToJoin();
    }

    private View setMemView(Member mem, LinearLayout _container) {
        TextView tv;

        View v = inflater.inflate(R.layout.memberlist_member, null);
        _container.addView(v);

        v.findViewById(R.id.profile_image).setClipToOutline(true);

        ((TextView) v.findViewById(R.id.memList_id)).setText(Integer.toString(mem.getId()));

        String str_name_age = mem.getName() + "(" + mem.getAge() + ")";
        tv = v.findViewById(R.id.memList_name);
        tv.setText(str_name_age);

        return v;
    }


    private void showList() {
        TextView tv;
        LinearLayout l;

        tv = (TextView) findViewById(R.id.memList_new_count);
        tv.setText("승인 대기 중인 인원(" + memList_new.size() + ")");
        for (int i = 0; i < memList_new.size(); i++) {
            final Member mem = memList_new.get(i);

            View v = setMemView(mem, container_new);

            v.findViewById(R.id.memList_btn).setVisibility(View.VISIBLE);
            Button bt = v.findViewById(R.id.memList_btn_accept);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accept(app.getGroupId(),String.valueOf(mem.getId()));
                    alert.setMessage("프로필 사진 불러오기 실패").setPositiveButton("확인", null);
                    alert.show();
                }
            });
            l = v.findViewById(R.id.memList_back);
            l.setBackground(ContextCompat.getDrawable(this, R.drawable.border_new));
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(app.getHostip())
                    .port(Integer.parseInt(app.getPort()))
                    .addPathSegment("images")
                    .addPathSegment(mem.getProfileimg())
                    .build();
            Glide.with(this).load(httpUrl.toString()).into((ImageView) v.findViewById(R.id.profile_image));
        }

        for (int i = 0; i < memList.size(); i++) {

            final Member mem = memList.get(i);

            View v = setMemView(mem, container);

            v.findViewById(R.id.memList_btn).setVisibility(View.GONE);
            l = v.findViewById(R.id.memList_back);

            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goMyPage(view , mem.getName(), mem.getPhone(),mem.getEmail(),mem.getIntro(),String.valueOf(mem.getAge()),String.valueOf(mem.getId()),app.getUid());
                }
            });
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(app.getHostip())
                    .port(Integer.parseInt(app.getPort()))
                    .addPathSegment("images")
                    .addPathSegment(mem.getProfileimg())
                    .build();
            Glide.with(this).load(httpUrl.toString()).into((ImageView) v.findViewById(R.id.profile_image));
        }

    }

    public void hide_new(View view) {

        LinearLayout l;
        l = (LinearLayout) findViewById(R.id.memberList_container_new);

        if (l.getVisibility() == View.VISIBLE) {
            l.setVisibility(View.GONE);
        } else {
            l.setVisibility(View.VISIBLE);
        }
    }

    public void goMyPage(View view ,String name,String phone,String email,String intro,String age, String RUid , String MyUid ) {
        Intent intent = new Intent(this, Mypage.class);
        intent.putExtra("Name",name);
        intent.putExtra("Phone",phone);
        intent.putExtra("Email",email);
        intent.putExtra("Intro",intro);
        intent.putExtra("Age",age);
        intent.putExtra("Receiver",RUid);
        intent.putExtra("UID",MyUid);
        startActivity(intent);
    }

    private void getMemberList() {
        String Host = "http://18.206.18.154:";
        String port = "8080";
        String AccessPath = "/GuestBook/" + app.getGroupId() + "/MemberList";
        final String url = Host + port + AccessPath;
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                com.example.pda.entity.Member[] groupMembers = gson.fromJson(string, com.example.pda.entity.Member[].class);
                for (com.example.pda.entity.Member groupMember : groupMembers) {
                    String introduction = groupMember.getIntroduction();
                    if (introduction == null) {
                        introduction = " ";
                    }
                    memList.add(new Member(groupMember.getUId(), groupMember.getName(), groupMember.getAge(), groupMember.getPhone(), groupMember.getEmail(), introduction , groupMember.getProfileimg()));
                }
            }
        };
        new Thread(networkTask).run();
//        showList();
    }

    private void getWaitingToJoin()
    {
        String Host = "http://18.206.18.154:";
        String port = "8080";
        String AccessPath = "/GuestBook/" + app.getGroupId() + "/waitingToJoin";
        final String url = Host + port + AccessPath;
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                com.example.pda.entity.Member[] groupMembers = gson.fromJson(string, com.example.pda.entity.Member[].class);
                for (com.example.pda.entity.Member groupMember : groupMembers) {
                    String introduction = groupMember.getIntroduction();
                    if (introduction == null) {
                        introduction = " ";
                    }
                    memList_new.add(new Member(groupMember.getUId(), groupMember.getName(), groupMember.getAge(), groupMember.getPhone(), groupMember.getEmail(), introduction , groupMember.getProfileimg()));
                }
            }
        };
        new Thread(networkTask).run();
        showList();
    }

    private void accept(String UID , String MemberUID )
    {
        String Host = "http://18.206.18.154:";
        String port = "8080";
        String AccessPath = "/JoinGroup/Certification/";
        final String url = Host + port + AccessPath + UID + "/"+MemberUID;
        System.out.println("url = " + url);
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(networkTask).run();
    }
}