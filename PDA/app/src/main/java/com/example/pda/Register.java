package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {
    private AlertDialog.Builder alert;
    private Bitmap scaled;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;
    private boolean dupcheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dupcheck = false;
        ((EditText)findViewById(R.id.newID)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                dupcheck = false;
            }
        });
        alert = new AlertDialog.Builder(this);
    }

    public void loadProfile(View view){
        doTakeAlbumAction();
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
                scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                ImageView iv = findViewById(R.id.profile_image);
                iv.setImageBitmap(scaled);
                Glide.with(this).load(uri).into(iv);

                ((TextView)findViewById(R.id.profile_image_url)).setText(uri.toString());

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void showDatePicker2(View view){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.fragment_date_picker);

        TextView tv = findViewById(R.id.register_birth);
        String str_date = (String)tv.getText();

        int year = Integer.parseInt(str_date.substring(0,4));
        int month = Integer.parseInt(str_date.substring(4,6))-1;
        int day = Integer.parseInt(str_date.substring(6,8));

        DatePicker datePicker = dialog.findViewById(R.id.datePicker);
        datePicker.updateDate(year, month, day);
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int y, int m, int d) {
                TextView _tv = findViewById(R.id.register_birth);
                _tv.setText(String.format("%d%02d%02d",y,m+1,d));
            }
        });

        dialog.show();
    }


    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        String dateMessage = (year_string+"/"+month_string + "/" + day_string);
    }

    public void doRegister(View view)
    {
        if(dupcheck == true) {
            if(((EditText)findViewById(R.id.newPW)).getText().toString().equals(((EditText)findViewById(R.id.checkPW)).getText().toString())) {
                uploadImage();
                OkHttpClient client = new OkHttpClient();

                HttpUrl httpUrl = new HttpUrl.Builder()
                        .scheme("http")
                        .host("18.206.18.154")
                        .port(8080)
                        .addPathSegment("auth")
                        .build();

                String json = "";
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("name", ((EditText)findViewById(R.id.name)).getText().toString());
                    jsonObject.accumulate("id", ((EditText)findViewById(R.id.newID)).getText().toString());
                    Map<String, Object> ret = HashingF(((EditText)findViewById(R.id.newPW)).getText().toString());
                    jsonObject.accumulate("passHash", ret.get("hash"));
                    jsonObject.accumulate("passSalt", ret.get("salt"));
                    jsonObject.accumulate("age", getAge(((TextView)findViewById(R.id.register_birth)).getText().toString()));
                    jsonObject.accumulate("birth", ((TextView)findViewById(R.id.register_birth)).getText().toString());
                    jsonObject.accumulate("email", ((EditText)findViewById(R.id.email)).getText().toString());
                    jsonObject.accumulate("kakao", ((EditText)findViewById(R.id.kakao)).getText().toString());
                    jsonObject.accumulate("phone", ((EditText)findViewById(R.id.telephonenum)).getText().toString());
                    jsonObject.accumulate("profileimg", ((TextView)findViewById(R.id.profile_image_url)).getText().toString() + "profile.png");
                    jsonObject.accumulate("introduction", ((EditText)findViewById(R.id.introduction)).getText().toString());
                    jsonObject.accumulate("joinedgroups", "");
                    jsonObject.accumulate("awaitingcertification", "");
                    json = jsonObject.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .post(reqBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        alert.setMessage("회원가입 실패").setPositiveButton("확인", null);
                        alert.show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    }
                });
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();

            }
            else
            {
                alert.setMessage("비밀번호 불일치").setPositiveButton("확인", null);
                alert.show();
            }
        }
        else
        {
            alert.setMessage("ID 중복확인을 해주십시오").setPositiveButton("확인", null);
            alert.show();
        }
    }

    public void uploadImage()
    {
        OkHttpClient client = new OkHttpClient();
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("18.206.18.154")
                .port(8080)
                .addPathSegment("image/profile" +  System.currentTimeMillis()+ ".png")
                .build();

        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
        scaled.compress( Bitmap.CompressFormat.PNG, 100, stream) ;
        byte[] byteArray = stream.toByteArray();
        RequestBody reqBody = RequestBody.create(byteArray);
        Request request = new Request.Builder()
                .url(httpUrl)
                .post(reqBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                alert.setMessage("이미지 업로드 실패").setPositiveButton("확인", null);
                alert.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                
            }
        });
    }

    public void doDupcheck(View view)
    {
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("18.206.18.154")
                .port(8080)
                .addPathSegment(((EditText)findViewById(R.id.newID)).getText().toString())
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                alert.setMessage("중복입니다").setPositiveButton("확인", null);
                alert.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                dupcheck = true;
                ((TextView)findViewById(R.id.dup)).setText("확인완료");
            }
        });
    }

    public Map<String, Object> HashingF(String pw) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String , Object> ret = new HashMap<>();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        ret.put("pw", pw);
        ret.put("salt", getRandomStr(32));
        String target = (String)ret.get("pw") + (String)ret.get("salt");
        byte[] hash = digest.digest(target.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        ret.put("hash", hexString.toString());
        return ret;
    }

    public String getRandomStr(int size) {
        if(size > 0) {
            char[] tmp = new char[size];
            for(int i=0; i<tmp.length; i++) {
                int div = (int) Math.floor( Math.random() * 2 );

                if(div == 0) {
                    tmp[i] = (char) (Math.random() * 10 + '0') ;
                }else {
                    tmp[i] = (char) (Math.random() * 26 + 'A') ;
                }
            }
            return new String(tmp);
        }
        return "ERROR : Size is required.";
    }

    public int getAge(String birth)
    {
        Calendar cal = Calendar.getInstance();

        return  cal.get(Calendar.YEAR) - Integer.parseInt(birth.substring(0,4)) + 1;
    }
}