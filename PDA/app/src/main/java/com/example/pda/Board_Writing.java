package com.example.pda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Board_Writing extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;
    private LinearLayout container;
    private LinearLayout survey_container;
    private LayoutInflater inflater;
    int survey_ch=0;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__writing);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2);
        ////////////////////////

        container = (LinearLayout)findViewById(R.id.boardWriting_container);
        survey_container = (LinearLayout)findViewById(R.id.boardWriting_survey_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        //tvList = new ArrayList<>();
    }

    public void addPicture(View view) {
        doTakeAlbumAction();
    }

    /**

     * 앨범에서 이미지 가져오기

     */

    private void doTakeAlbumAction()
    {
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

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                View v = inflater.inflate(R.layout.board_writing_image, null);
                container.addView(v);
                TextView tv = v.findViewById(R.id.boardWritingImg_textView);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        container.removeView((View) view.getParent());
                    }
                });

                ImageView iv = v.findViewById(R.id.boardWritingImg_imgView);
                iv.setImageBitmap(scaled);
                Glide.with(this).load(uri).into(iv);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    //설문조사 추가하기
    public void addSurvey(View view){

        if(survey_container.getVisibility() != View.VISIBLE)
        {
            survey_container.setVisibility(View.VISIBLE);

            View v = inflater.inflate(R.layout.survey_create, null);
            survey_container.addView(v);

            for(int i=0;i<2;i++)
            {
                View _v = inflater.inflate(R.layout.survey_create_list, null);
                ((LinearLayout)findViewById(R.id.survey_list_container)).addView(_v);
                ((TextView)_v.findViewById(R.id.survey_create_list_title)).setText("항목 "+(++survey_ch));
            }

            v.findViewById(R.id.survey_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View _v = inflater.inflate(R.layout.survey_create_list, null);
                    ((LinearLayout)findViewById(R.id.survey_list_container)).addView(_v);
                    ((TextView)_v.findViewById(R.id.survey_create_list_title)).setText("항목 "+(++survey_ch));
                }
            });
        }

    }

    public void goBoardWriting(View view){
        Intent intent = new Intent(this, Board_Writing.class);
        startActivity(intent);
    }
}