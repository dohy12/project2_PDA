package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class memberList extends AppCompatActivity {
    ArrayList<Member> memList;
    ArrayList<Member> memList_new;
    private LinearLayout container;
    private LinearLayout container_new;
    private LayoutInflater inflater;

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
        ((TextView)findViewById(R.id.memList_name)).setText(app.getName()+"(" + app.getAge() + ")");
        ((TextView)findViewById(R.id.memList_name)).setText(app.getName()+"(" + app.getAge() + ")");

        container = (LinearLayout) findViewById(R.id.memberList_container);
        container_new = (LinearLayout) findViewById(R.id.memberList_container_new);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        memList_new = new ArrayList<>();
//        memList_new.add(new Member(Integer.parseInt(app.getUid()), app.getName(), Integer.parseInt(app.getAge()), app.getPhone(), app.getEmail(), app.getIntro()));

        TextView textView = findViewById(R.id.memList_name);
        textView.setText(app.getName() + "(" + app.getAge().toString() + ")");
        textView = findViewById(R.id.memList_phone);
        textView.setText(app.getPhone());

        memList = new ArrayList<>();
        getMemberList();
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
            Member mem = memList_new.get(i);

            View v = setMemView(mem, container_new);

            v.findViewById(R.id.memList_btn).setVisibility(View.VISIBLE);
            l = v.findViewById(R.id.memList_back);
            l.setBackground(ContextCompat.getDrawable(this, R.drawable.border_new));
        }

        for (int i = 0; i < memList.size(); i++) {

            Member mem = memList.get(i);

            View v = setMemView(mem, container);

            v.findViewById(R.id.memList_btn).setVisibility(View.GONE);
            l = v.findViewById(R.id.memList_back);

            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goMyPage(view);
                }
            });

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

    public void goMyPage(View view) {
        Intent intent = new Intent(this, Mypage.class);
        startActivity(intent);
    }

    private void getMemberList() {
        String Host = "http://10.0.2.2:";
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
                    memList.add(new Member(groupMember.getUId(), groupMember.getName(), groupMember.getAge(), groupMember.getPhone(), groupMember.getEmail(), introduction));
                }
            }
        };
        new Thread(networkTask).run();
        showList();
    }

}