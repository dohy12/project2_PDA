package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.spec.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.Cipher;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goSelectGroup(View view){
        if(((EditText)findViewById(R.id.ID)).getText().toString().length() == 0 || ((EditText)findViewById(R.id.PW)).getText().toString().length() == 0)
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("아이디와 비번을 모두 입력하세요.").setPositiveButton("확인", null);
            alert.show();
        }
        else {
            String JWT = doLogin();
            if (JWT.length() > 0) {
                Intent intent = new Intent(this, SelectGroup.class);
                intent.putExtra("JWT", JWT);
                startActivity(intent);
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.").setPositiveButton("확인", null);
                alert.show();
            }
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
        String result = "";
        try {
            Date dnow = new Date(System.currentTimeMillis());
            SimpleDateFormat datef = new SimpleDateFormat("ss");
            URL url = new URL("http://localhost:8080/auth?id=" + ID + "&pw=" + EncryptRSA(datef.format(dnow) + ":" + PW));
            System.out.println(url.toString());
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
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

    public String EncryptRSA(String plain) throws Exception {
        System.out.println(plain);
        InputStream fis = getResources().openRawResource(R.raw.pdkey);
        byte[] keyBytes = new byte[fis.available()];
        fis.read(keyBytes);
        fis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        System.out.println(kf.generatePublic(spec));
        Cipher cip = Cipher.getInstance("RSA");
        cip.init(Cipher.ENCRYPT_MODE, kf.generatePublic(spec));

        System.out.println(plain);
        byte[] en = cip.doFinal(plain.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(en));
        return Base64.getEncoder().encodeToString(en);
    }
}