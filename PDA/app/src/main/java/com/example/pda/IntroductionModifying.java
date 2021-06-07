package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IntroductionModifying extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;
    private LinearLayout image_container;
    private LayoutInflater inflater;
    private List<Bitmap> bitmapList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_modifying);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        image_container = findViewById(R.id.board_image_container);

        bitmapList = new ArrayList<>();
    }

    public void addPicture(View view) {
        doTakeAlbumAction();
    }

    private void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                View v = inflater.inflate(R.layout.board_writing_image, null);
                image_container.addView(v);
                TextView tv = v.findViewById(R.id.boardWritingImg_textView);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        image_container.removeView((View) view.getParent());
                    }
                });

                ImageView iv = v.findViewById(R.id.boardWritingImg_imgView);
                iv.setImageBitmap(scaled);
                Glide.with(this).load(uri).into(iv);
                bitmapList.add(scaled);
                //uploadImage("sadf.png", scaled);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private class DeleteImagesrcRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        public DeleteImagesrcRunnable(RetrofitService service, String GroupId) {
            this.service = service;
            this.GroupId = GroupId;
        }
        public void run() {
            retrofit2.Call<String> call = service.DeleteintroImage(GroupId);
            try {
                call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveImagesrcRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        private String image_src;
        public SaveImagesrcRunnable(RetrofitService service, String GroupId, String image_src) {
            this.service = service;
            this.GroupId = GroupId;
            this.image_src = image_src;
        }
        public void run() {
            retrofit2.Call<String> call = service.SaveintroImage(GroupId, image_src);
            try {
                call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class DeleteContentsRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        public DeleteContentsRunnable(RetrofitService service, String GroupId) {
            this.service = service;
            this.GroupId = GroupId;
        }
        public void run() {
            retrofit2.Call<String> call = service.DeleteintroContents(GroupId);
            try {
                call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveContentsRunnable implements Runnable {
        RetrofitService service;
        private String GroupId;
        private String contents;
        public SaveContentsRunnable(RetrofitService service, String GroupId, String contents) {
            this.service = service;
            this.GroupId = GroupId;
            this.contents = contents;
        }
        public void run() {
            retrofit2.Call<String> call = service.SaveintroContents(GroupId, contents);
            try {
                call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImage(String name, Bitmap scaled) {
        if (scaled != null) {
            OkHttpClient client = new OkHttpClient();

            //이미지 등록은 /image/{name} 으로 포스트 요청을 해서 등록을 합니다.
            HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host(app.getHostip())
                    .port(Integer.parseInt(app.getPort()))
                    .addPathSegment("image")
                    .addPathSegment(name)
                    .build();

            System.out.println(httpUrl);

            //Bitmap으로 되어있는 이미지를 requestbody에 넣는 과정
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            scaled.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            final RequestBody reqBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", "", RequestBody.create(MultipartBody.FORM, byteArray)).build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .post(reqBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                }
            });
        }
    }
    public void onSubmitClicked(View view) {
        String origin = "http://18.206.18.154:8080/";
        String GroupId = app.getGroupId();
        RetrofitService service1;
        Runnable r1;
        Thread thread1;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(origin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service1 = retrofit.create(RetrofitService.class);

        r1 = new DeleteImagesrcRunnable(service1, GroupId);
        thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) {
        }

        for(int i=0; i<bitmapList.size(); i++) {
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
            Date date = new Date();
            String dateToStr = dateFormat.format(date);
            String image_src = "GroupIntroImage" + dateToStr + ".png";

            uploadImage(image_src, bitmapList.get(i));
            r1 = new SaveImagesrcRunnable(service1, GroupId, image_src);
            thread1 = new Thread(r1);

            thread1.start();

            try {
                thread1.join();
            } catch (Exception e) {
            }
        }

        TextView tv = findViewById(R.id.EditText);
        String contents = tv.getText().toString();

        r1 = new DeleteContentsRunnable(service1, GroupId);
        thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) {
        }

        r1 = new SaveContentsRunnable(service1, GroupId, contents);
        thread1 = new Thread(r1);

        thread1.start();

        try {
            thread1.join();
        } catch (Exception e) {
        }

        finish();
    }
}
