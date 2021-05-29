package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Drawer drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///툴바 세팅/////////////
        drawer = new Drawer(findViewById(R.id.drawerBar));
        toolbar = new Toolbar(findViewById(R.id.toolbar), drawer);
        ////////////////////////
    }

    public void goMemList(View view){
        Intent intent = new Intent(this, memberList.class);
        startActivity(intent);
    }

    public void goBoard1(View view){
        Intent intent = new Intent(this, Board.class);
        startActivity(intent);
    }

    public void goBoard2(View view){
        Intent intent = new Intent(this, Board.class);
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

    
}