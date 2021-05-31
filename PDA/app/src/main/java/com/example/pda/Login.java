package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goSelectGroup(View view){
        String JWT = doLogin();
        if(JWT.length() > 0) {
            Intent intent = new Intent(this, SelectGroup.class);
            intent.putExtra("JWT", JWT);
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.").setPositiveButton("확인", null);
        }
    }

    public void goRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //JWT 토큰 반환
    public String doLogin()
    {
        String ID = ((EditText)findViewById(R.id.ID)).getText().toString();
        String PW = ((EditText)findViewById(R.id.PW)).getText().toString();
        HttpURLConnection httpConn = null;
        RSAEncoder rsa = new RSAEncoder();
        String result = "";
        try {
            URL url = new URL("http://localhost:8080/auth?id=" + ID + "&pw=" + rsa.EncryptRSA(System.currentTimeMillis() + ":" + PW));
            httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int code = httpConn.getResponseCode();

            if (code == 200)
            {
                InputStream response = httpConn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(response, "UTF-8");
                BufferedReader bf = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer();
                String temp;
                while ((temp = bf.readLine()) != null) {
                    sb.append(temp);
                }
                JSONObject json = new JSONObject(sb.toString());
                result = json.getString("JWT");
            }
        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null)
                httpConn.disconnect();
        }

        return result;
    }
}