package com.example.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView payWebView;
    private static final String APP_SCHEME = "iamporttest://";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payWebView = (WebView)findViewById(R.id.payWebView);
        payWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = payWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(payWebView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();


        if ( intentData == null ) {
            payWebView.loadUrl("https://dohy12.github.io/project2_PDA/HTML/Payment.html");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length()+3);
                payWebView.loadUrl(redirectURL);
            }
        }
    }
}
