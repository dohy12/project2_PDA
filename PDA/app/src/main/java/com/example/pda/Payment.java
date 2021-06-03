package com.example.pda;

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

import java.util.List;

public class Payment extends AppCompatActivity {
    private WebView payWebView;
    private final String APP_SCHEME = "iamportapp"; //결제 성공시 돌아갈 주소?
    private String webview_url = "file:///android_asset/WebView.html"; //local assets directory에 저장
    private int P_ID;

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
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
                payWebView.loadUrl(redirectURL);
            }
        }
    }

    //앱에서 웹뷰로 정보 넘겨주기 위한 함수들 - 현재는 값들 하드코딩 되어있음
    public class WebAppInterface {
        /** Show a toast from the web page */
        @JavascriptInterface
        public String return_JWT() {
            return "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTAzMjM4NTQ5IiwiZXhwIjoxNjIyNjk1NzAzLCJpYXQiOjE2MjI2OTM5MDN9.Sj0boAjOB7AQ0d_b7tpHo5ETarDh7fghlA7piImRJQvfHVaWSIQA8IO4OzBIvBk9Irq2bv4rycNZdHlwsIcS0g";
        }

        @JavascriptInterface
        public String return_GroupId() {
            return "deaa01013b0144e99faab90ecd670950";
        }

        @JavascriptInterface
        public int return_PID() {
            return P_ID;
        }

        @JavascriptInterface
        public int return_UID() {
            return 1503238549;
        }
    }
}
