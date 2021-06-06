package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pda.entity.Group;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SelectGroup extends AppCompatActivity {
    private AlertDialog.Builder alert;
    private LinearLayout container;
    private LinearLayout Awaiting_container;
    private LayoutInflater inflater;
    ArrayList<Group> Joined;
    ArrayList<Group> Awaiting;

    final Handler serverhandler = new Handler(){
        public void handleMessage(Message msg){
            alert.setMessage("서버와의 연결이 원활하지 않습니다.").setPositiveButton("확인", null);
            alert.show();
        }
    };

    final Handler failhandler = new Handler(){
        public void handleMessage(Message msg){
            alert.setMessage("그룹 프로필 불러오기 실패").setPositiveButton("확인", null);
            alert.show();
        }
    };

    final Handler joinedhandler = new Handler(){
        public void handleMessage(Message msg){
            showGroupJoined();
        }
    };

    final Handler awaitinghandler = new Handler(){
        public void handleMessage(Message msg){
            showGroupAwaiting();
        }
    };

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        mContext = this;

        alert = new AlertDialog.Builder(this);
        System.out.println(app.getJWT());
        container = findViewById(R.id.container);
        Awaiting_container = findViewById(R.id.container_waiting);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        setProfileInfo();

        //가입된 그룹 가져오기
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(app.getHostip())
                .port(Integer.parseInt(app.getPort()))
                .addPathSegment("group")
                .addPathSegment("Joined")
                .addPathSegment(app.getID())
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("JWT",app.getJWT())
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message msg = serverhandler.obtainMessage();
                serverhandler.sendMessage(msg);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code() == 200)
                {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    System.out.println("string = " + string);
                    Joined = gson.fromJson(string, new TypeToken<List<Group>>(){}.getType());
                    Message msg = joinedhandler.obtainMessage();
                    joinedhandler.sendMessage(msg);
                }
                else
                {
                    Message msg = failhandler.obtainMessage();
                    failhandler.sendMessage(msg);
                }
            }
        });

        //대기중인 그룹 가져오기
        httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(app.getHostip())
                .port(Integer.parseInt(app.getPort()))
                .addPathSegment("group")
                .addPathSegment("Awaiting")
                .addPathSegment(app.getID())
                .build();

        request = new Request.Builder()
                .url(httpUrl)
                .addHeader("JWT",app.getJWT())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message msg = serverhandler.obtainMessage();
                serverhandler.sendMessage(msg);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.code() == 200)
                {
                    Gson gson = new Gson();
                    String string = response.body().string();
                    System.out.println("string = " + string);
                    Awaiting = gson.fromJson(string, new TypeToken<List<Group>>(){}.getType());
                    Message msg = awaitinghandler.obtainMessage();
                    awaitinghandler.sendMessage(msg);
                }
                else
                {
                    Message msg = failhandler.obtainMessage();
                    failhandler.sendMessage(msg);
                }
            }
        });
    }

    private void setProfileInfo(){
        ImageView profile =(ImageView) findViewById(R.id.profile_image);
        profile.setImageBitmap(app.getProfile());
        findViewById(R.id.profile_image).setClipToOutline(true);
        ((TextView)findViewById(R.id.welcome)).setText(app.getName()+ "님 반갑습니다.");
    }

    private void showGroupJoined(){
        for(int i=0;i<Math.ceil(Joined.size()/3.0f);i++) {
            int maxJ = Math.min(3, Joined.size()-i*3);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.LEFT;

            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(layoutParams);
            l.setOrientation(LinearLayout.HORIZONTAL);

            container.addView(l);
            for(int j=0;j<maxJ ; j++){
                View v = inflater.inflate(R.layout.group_layout_waiting, null);
                l.addView(v);

                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme("http")
                        .host(app.getHostip())
                        .port(Integer.parseInt(app.getPort()))
                        .addPathSegment("images")
                        .addPathSegment(Joined.get(i).getGroupImg())
                        .build();
                Group gt = Joined.get(i*3+j);
                ((TextView)v.findViewById(R.id.group_name)).setText(gt.getName());
                Glide.with(this).load(httpUrl.toString()).into((ImageView)v.findViewById(R.id.group_layout_profile));
                LinearLayout groupLayout = v.findViewById(R.id.group_layout);
                groupLayout.setClipToOutline(true);


                /// group_id 넣기
                ((TextView)v.findViewById(R.id.group_groupID)).setText(gt.getGroupId());

                /// "신청 대기중" TextView 숨기기
                v.findViewById(R.id.group_waitingCheck).setVisibility(View.GONE);


                groupLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        app.setGroupId(((TextView)view.findViewById(R.id.group_groupID)).getText().toString());
                        app.setGroupName(((TextView)view.findViewById(R.id.group_name)).getText().toString());
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }
    }

    private void showGroupAwaiting(){
        for(int i=0;i<Math.ceil(Awaiting.size()/3.0f);i++) {
            int maxJ = Math.min(3, Awaiting.size()-i*3);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.LEFT;

            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(layoutParams);
            l.setOrientation(LinearLayout.HORIZONTAL);

            Awaiting_container.addView(l);
            for(int j=0;j<maxJ ; j++){
                View v = inflater.inflate(R.layout.group_layout_waiting, null);
                l.addView(v);

                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme("http")
                        .host(app.getHostip())
                        .port(Integer.parseInt(app.getPort()))
                        .addPathSegment("images")
                        .addPathSegment(Awaiting.get(i).getGroupImg())
                        .build();
                Group gt = Awaiting.get(i*3+j);
                ((TextView)v.findViewById(R.id.group_name)).setText(gt.getName());
                Glide.with(this).load(httpUrl.toString()).into((ImageView)v.findViewById(R.id.group_layout_profile));
                LinearLayout groupLayout = v.findViewById(R.id.group_layout);
                groupLayout.setClipToOutline(true);

                /// group_id 넣기
                ((TextView)v.findViewById(R.id.group_groupID)).setText(gt.getGroupId());
            }
        }
    }

    public void applyGroup(View view){
        GroupListFragment fragment = new GroupListFragment(this);

        fragment.show(getSupportFragmentManager(),"tag");

    }

    public void logOut(){
        finish();
    }
}