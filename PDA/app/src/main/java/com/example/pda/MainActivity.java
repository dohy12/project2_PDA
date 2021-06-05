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
}