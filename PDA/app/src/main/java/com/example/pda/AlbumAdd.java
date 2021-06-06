package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class AlbumAdd extends AppCompatActivity {
    private LinearLayout image_container;
    private LayoutInflater inflater;

    private Bitmap scaled;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;

    private WebView webView;
    private Dialog dialog;
    private TextView tv_address;

    private Map<Integer, Bitmap> imageBitMapMap;
    private int imageBitMapChecker;

    private AlertDialog.Builder alert;

    private String alertmsg = "";

    final Handler serverhandler = new Handler(){
        public void handleMessage(Message msg){
            alert.setMessage("업로드에 실패하였습니다.").setPositiveButton("확인", null);
            alert.show();

            btnEnable(true);
        }
    };

    final Handler alerthander = new Handler(){
        public void handleMessage(Message msg){
            alert.setMessage(alertmsg).setPositiveButton("확인", null);
            alert.show();
        }
    };


    final Handler testhandler = new Handler(){
        public void handleMessage(Message msg){
            alert.setMessage("업로드 완료.").setPositiveButton("확인", null);
            alert.show();

            btnEnable(true);
        }
    };

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_add);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        image_container = findViewById(R.id.image_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        tv_address = findViewById(R.id.album_add_location);

        imageBitMapMap = new HashMap<>();
        imageBitMapChecker = 0;

        alert = new AlertDialog.Builder(this);

        setDate();
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


                ///imageBitMapMap에 bitmap 추가
                imageBitMapMap.put(imageBitMapChecker, scaled);
                ((TextView)imageAddView.findViewById(R.id.album_list_id)).setText(Integer.toString(imageBitMapChecker));
                imageBitMapChecker++;
                //////////////////////////

                ((TextView)imageAddView.findViewById(R.id.title)).setText(uri.toString());

                imageAddView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = Integer.parseInt(((TextView)imageAddView.findViewById(R.id.album_list_id)).getText().toString());
                        imageBitMapMap.remove(id);
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

    public void sendToServer(View view){
        btnEnable(false);

        String title = ((EditText)findViewById(R.id.album_add_title)).getText().toString();
        String date_str = ((TextView)findViewById(R.id.album_date)).getText().toString();
        String location = ((TextView)findViewById(R.id.album_add_location)).getText().toString();
        String intro = ((EditText)findViewById(R.id.album_add_intro)).getText().toString();
        int uid = Integer.parseInt(app.getUid());

        int yy = Integer.parseInt(date_str.substring(0,4));
        int mm = Integer.parseInt(date_str.substring(6,8));
        int dd = Integer.parseInt(date_str.substring(10,12));

        String date_format = String.format("%d%02d%02d",yy,mm,dd);

        Album_info albumInfo = new Album_info(-1, title, date_format, location, uid, intro);
        String json = albumInfo.showJsonString();

        System.out.println("json : " + json);

        //////////////////////////////////////
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url("http://"+ app.getHostip() +":8080/album/" + app.getGroupId())
                .post(body)
                .build();

        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Message msg = serverhandler.obtainMessage();
                    serverhandler.sendMessage(msg);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Object jsonResult = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonResult));

                        int a_id = jsonObject.getInt("A_ID");

                        sendImageToServer(a_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendImageToServer(int a_id){
        for(Integer key : imageBitMapMap.keySet()){
            System.out.println("key : "+key);
            uploadImage(a_id, imageBitMapMap.get(key), key);
        }
    }

    public void uploadImage(int a_id, Bitmap bitmap, int bitmap_id)
    {
        //String name = "album_" + a_id + "_" + bitmap_id + ".png";
        OkHttpClient client = new OkHttpClient();

        //이미지 등록은 /image/{name} 으로 포스트 요청을 해서 등록을 합니다.
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(app.getHostip())
                .port(Integer.parseInt(app.getPort()))
                .addPathSegment("image_album")
                .addPathSegment(app.getGroupId())
                .addPathSegment(Integer.toString(a_id))
                .addPathSegment(Integer.toString(bitmap_id))
                .build();

        System.out.println(httpUrl);

        //Bitmap으로 되어있는 이미지를 requestbody에 넣는 과정
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        final RequestBody reqBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", "", RequestBody.create(MultipartBody.FORM, byteArray)).build();


        Request request = new Request.Builder()
                .url(httpUrl)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message msg = serverhandler.obtainMessage();
                serverhandler.sendMessage(msg);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    System.out.println(response.body().string());
                    alertmsg = "이미지 저장 실패";
                    Message msg = alerthander.obtainMessage();
                    alerthander.sendMessage(msg);
                }
                else{
                    ((Album)Album.mContext).refresh();

                    Intent intent = new Intent(AlbumAdd.this, Album.class);
                    startActivity(intent);

                    finish();
                }
            }
        });


    }


    private void btnEnable(boolean enable){
        findViewById(R.id.sendButton).setEnabled(enable);
    }



}