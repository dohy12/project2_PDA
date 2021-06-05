package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Login extends AppCompatActivity {
    private AlertDialog.Builder alert;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);;
        alert = new AlertDialog.Builder(this);
    }

    public void goSelectGroup(View view){
        intent = new Intent(this, SelectGroup.class);
        if(((EditText)findViewById(R.id.ID)).getText().toString().length() == 0 || ((EditText)findViewById(R.id.PW)).getText().toString().length() == 0)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("아이디와 비번을 모두 입력하세요.").setPositiveButton("확인", null);
            alert.show();
        }
        else {
            String ID = ((EditText)findViewById(R.id.ID)).getText().toString();
            String PW = ((EditText)findViewById(R.id.PW)).getText().toString();
            app.setID(ID);
            app.setEnPW(EncryptRSA(PW));
            OkHttpClient client = new OkHttpClient();

            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host("10.0.2.2")
                    .port(8080)
                    .addPathSegment("auth")
                    .addQueryParameter("id", ID)
                    .build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("pw", app.getEnPW())
                    .build();

            System.out.println(httpUrl);
            System.out.println(request.headers().toString());

            final Handler serverhandler = new Handler(){
                public void handleMessage(Message msg){
                    alert.setMessage("서버와의 연결이 원활하지 않습니다.").setPositiveButton("확인", null);
                    alert.show();
                }
            };

            final Handler loginhandler = new Handler(){
                public void handleMessage(Message msg){
                    alert.setMessage("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.").setPositiveButton("확인", null);
                    alert.show();
                }
            };

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                    System.out.println("로그인 실패");
                    Message msg = serverhandler.obtainMessage();
                    serverhandler.sendMessage(msg);
                }

                @Override
                public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        app.setJWT(json.getString("JWT"));
                        app.setName(json.getString("name"));
                        app.setUid(json.getString("UID"));
                        app.getUserInf();
                        startActivity(intent);
                    } catch (JSONException e) {
                        Message msg = loginhandler.obtainMessage();
                        loginhandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void goRegister(View view){
        intent = new Intent(this, Register.class);
        startActivity(intent);
    }


    public String EncryptRSA(String plain) {
        InputStream fis = getResources().openRawResource(R.raw.pdkey);
        byte[] keyBytes;
        try {
            keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            fis.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            Cipher cip = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cip.init(Cipher.ENCRYPT_MODE, kf.generatePublic(spec));
            byte[] en = cip.doFinal(plain.getBytes());
            return Base64.getEncoder().encodeToString(en);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}