package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
}