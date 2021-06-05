package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AlbumAdd extends AppCompatActivity {
    private LinearLayout name_container;
    private LinearLayout image_container;
    private LayoutInflater inflater;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;

    private WebView webView;
    private Dialog dialog;
    private TextView tv_address;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_add);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        image_container = findViewById(R.id.image_container);
        name_container = findViewById(R.id.name_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        tv_address = findViewById(R.id.album_add_location);

        setDate();
        testNameContainer();
    }

    public void addImage(View view){
        doTakeAlbumAction();
    }

    private void setDate(){
        Date today = new Date();

        SimpleDateFormat date = new SimpleDateFormat("yyyy. MM. dd");

        TextView _tv = findViewById(R.id.album_date);
        _tv.setText(date.format(today));
    }

    public void showDatePicker2(View view){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.fragment_date_picker);

        TextView tv = findViewById(R.id.album_date);
        String str_date = (String)tv.getText();

        int year = Integer.parseInt(str_date.substring(0,4));
        int month = Integer.parseInt(str_date.substring(6,8))-1;
        int day = Integer.parseInt(str_date.substring(10,12));

        DatePicker datePicker = dialog.findViewById(R.id.datePicker);
        datePicker.updateDate(year, month, day);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                TextView _tv = findViewById(R.id.album_date);
                _tv.setText(String.format("%d. %02d. %02d",y,m+1,d));
            }
        });

        dialog.show();


    }

    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                final View imageAddView = inflater.inflate(R.layout.album_add_list, null);
                image_container.addView(imageAddView);

                ImageView iv = imageAddView.findViewById(R.id.image);
                iv.setImageBitmap(scaled);
                Glide.with(this).load(uri).into(iv);

                ((TextView)imageAddView.findViewById(R.id.title)).setText(uri.toString());

                imageAddView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image_container.removeView(imageAddView);
                    }
                });

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void testNameContainer(){
        for(int i=0;i<5;i++){
            View nameAddView = inflater.inflate(R.layout.album_people, null);
            name_container.addView(nameAddView);

            nameAddView.findViewById(R.id.profile_image).setClipToOutline(true);
        }
    }

    private void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView_address);

        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setDomStorageEnabled(true);

        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);

        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");

        WebChromeClient webChromeClient = new WebChromeClient(){
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView dialog_web = new WebView(AlbumAdd.this);
                dialog_web.getSettings().setJavaScriptEnabled(true);
                dialog_web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                dialog_web.getSettings().setDomStorageEnabled(true);

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(dialog_web);
                resultMsg.sendToTarget();

                dialog = new Dialog(AlbumAdd.this);
                dialog.setContentView(dialog_web);

                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                dialog.show();
                dialog_web.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onCloseWindow(WebView window) {
                        dialog.dismiss();
                    }
                });

                // WebView Popup에서 내용이 안보이고 빈 화면만 보여 아래 코드 추가
                dialog_web.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return false;
                    }
                });

                return true;
            }
        };
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(webChromeClient);


        // webview url load. php 파일 주소
        webView.loadUrl("https://crabox.io/test/dohy/daum3.php");

    }

    private class AndroidBridge {
        @JavascriptInterface
        public void getAddress(final String arg1, final String arg2, final String arg3) {
            tv_address.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

            dialog.dismiss();
        }

    }

    public void editAddressOnclick(View View){
        init_webView();
    }

}