package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goLogin(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void goRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}