package com.example.pda;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Payment extends AppCompatActivity {
    private WebView payWebView;
    private final String APP_SCHEME = "iamportapp"; //결제 성공시 돌아갈 주소?
    private String webview_url = "file:///android_asset/WebView.html"; //local assets directory에 저장
    private int P_ID;
    private List<Integer> userdueInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //아래 주석은 구현해야할 사항
        /*
        결제 관리 페이지에서 결제 버튼 눌린 회비 정보 가져오기 구현 부분(인텐트 호출 시 정보 넘겨주는걸 받는 형태로?)
        가져올 정보 : P_ID, buyer_name
        이외 필요한 정보 : GroupId, JWT, UID(어떻게 가져올지 아직 모름)
        */

        /*
        결제 요청 정보 서버에 저장하는 REST API 콜 구현 부분
        url : http://서버/payments/request-infos (POST)
        body : {GroupId : "", P_ID : "", buyer_name: ""}
         */

        payWebView = (WebView)findViewById(R.id.payWebView);
        payWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = payWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        payWebView.addJavascriptInterface(new WebAppInterface(), "Android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(payWebView, true);
        }

        Intent intent = getIntent();
        P_ID = intent.getIntExtra("P_ID", 0);
        Uri intentData = intent.getData();

        /*
        결제 하고 다시 회비 관리 페이지로 돌아오는 기능 구현 부분
         */

        //아래 코드는 뭔지 확인해봐야함
        if ( intentData == null ) {
            payWebView.loadUrl(webview_url);
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            //setResult(100);
            //finish();
            String url = intentData.toString();
            System.out.println(url);
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
                //payWebView.loadUrl(redirectURL);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();

        String origin = "http://18.206.18.154:8080/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(origin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Runnable r1 = new UserDueInfosRunnable(service1, app.getGroupId(), app.getJWT());
        Thread thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) { }

        boolean is_payed = false;
        for(int i=0; i<userdueInfos.size(); i++) {
            if(userdueInfos.get(i) == P_ID) {
                is_payed = true;
                break;
            }
        }

        if(!is_payed) {
            r1 = new DeleteRequestRunnable(service1, app.getGroupId(), app.getJWT(), P_ID);
            thread1 = new Thread(r1);
            thread1.start();
            try {
                thread1.join();
            } catch (Exception e) { }

        }
        finish();
    }

    private class UserDueInfosRunnable implements Runnable {
        RetrofitService service;
        private String GroupId, JWT;
        public UserDueInfosRunnable(RetrofitService service, String GroupId, String JWT) {
            this.service = service;
            this.GroupId = GroupId;
            this.JWT = JWT;
        }
        public void run() {
            Call<List<Integer>> call = service.getUserDueInfos(GroupId, JWT);
            try {
                userdueInfos = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DeleteRequestRunnable implements Runnable {
        RetrofitService service;
        private String GroupId, JWT;
        private int P_ID;
        public DeleteRequestRunnable(RetrofitService service, String GroupId, String JWT, int P_ID) {
            this.service = service;
            this.GroupId = GroupId;
            this.JWT = JWT;
            this.P_ID = P_ID;
        }
        public void run() {
            Call<String> call = service.DeleteRequestInfos(GroupId, P_ID, JWT);
            try {
                String result = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //앱에서 웹뷰로 정보 넘겨주기 위한 함수들 - 현재는 값들 하드코딩 되어있음
    public class WebAppInterface {
        /** Show a toast from the web page */
        @JavascriptInterface
        public String return_JWT() {
            return app.getJWT();
            //return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTAzMjM4NTQ5IiwiZXhwIjoxNjIyNjk1NzAzLCJpYXQiOjE2MjI2OTM5MDN9.Sj0boAjOB7AQ0d_b7tpHo5ETarDh7fghlA7piImRJQvfHVaWSIQA8IO4OzBIvBk9Irq2bv4rycNZdHlwsIcS0g";
        }

        @JavascriptInterface
        public String return_GroupId() {
            return app.getGroupId();
            //return "deaa01013b0144e99faab90ecd670950";
        }

        @JavascriptInterface
        public int return_PID() {
            return P_ID;
        }

        @JavascriptInterface
        public int return_UID() {
            return Integer.parseInt(app.getUid());
            //return 1503238549;
        }

        @JavascriptInterface
        public String return_buyer_name() {
            return app.getName();
        }

        @JavascriptInterface
        public String return_email() {
            return app.getEmail();
        }
    }
}
