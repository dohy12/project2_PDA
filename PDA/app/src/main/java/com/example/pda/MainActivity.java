package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}