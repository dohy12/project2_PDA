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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Board_Writing extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_FROM_ALBUM = 1;
    private LinearLayout container;
    private LinearLayout survey_container;
    private LayoutInflater inflater;
    int survey_ch=0;
    Board_Info boardInfo;
    int nextbid = 0;

    ArrayList<Survey_Option> options = new ArrayList<Survey_Option>();
    ArrayList<Survey_Result> results = new ArrayList<Survey_Result>();
    Survey_Info survey = null;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board__writing);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        container = (LinearLayout)findViewById(R.id.boardWriting_container);
        survey_container = (LinearLayout)findViewById(R.id.boardWriting_survey_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        //tvList = new ArrayList<>();

        Intent myIntent = getIntent();
        boardInfo = (Board_Info) myIntent.getSerializableExtra("selectedBoard");

        if(boardInfo.getBoardId() != 0) {
            String beforeContents = boardInfo.getContents();
            String beforeTitle = boardInfo.getTitle();

            EditText contents = (EditText)findViewById(R.id.contents);
            EditText title = (EditText)findViewById(R.id.title);
            contents.setText(beforeContents);
            title.setText(beforeTitle);

            System.out.println(beforeContents);
        }
    }

    //등록 버튼 onClick 메소드
    public void wirteBoard(View v) {
        if(boardInfo.getBoardId() == 0) {
            String title = ((EditText) findViewById(R.id.title)).getText().toString();
            String contents = ((EditText) findViewById(R.id.contents)).getText().toString();
            int notice;
            int uid = Integer.parseInt(app.getUid());

            CheckBox isNotice = (CheckBox) findViewById(R.id.isNotice);
            if (isNotice.isChecked()) notice = 1;
            else notice = 0;

            OkHttpClient client = new OkHttpClient();

            String json = makeJSONString(nextbid, notice, title, contents, uid, 0);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url("http://18.206.18.154:8080/Community/" + app.getGroupId())
                    .addHeader("JWT", app.getJWT())
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            writeSurvey(survey);
            for(int i = 0; i < options.size(); i++)
                writeOption(options.get(i));
            for(int i = 0; i < results.size(); i++)
                writeResult(results.get(i));
        } else {
            String title = ((EditText)findViewById(R.id.title)).getText().toString();
            String contents = ((EditText)findViewById(R.id.contents)).getText().toString();
            int notice;
            int uid = Integer.parseInt(app.getUid());

            CheckBox isNotice = (CheckBox)findViewById(R.id.isNotice);
            if(isNotice.isChecked()) notice = 1;
            else notice = 0;

            OkHttpClient client = new OkHttpClient();

            String json = makeJSONString(0, notice, title, contents, uid, 0);

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, json);

            Request request = new Request.Builder()
                    .url("http://18.206.18.154:8080/Community/" + app.getGroupId() + "/" + boardInfo.getBoardId())
                    .addHeader("JWT", app.getJWT())
                    .put(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        finish();
    }

    //무결성 문제 방지를 위해 게시글 -> 설문 -> 옵션 -> 결과 순으로 저장되어야 함
    //writeBoard 다음에 순서대로 호출될 메소드들
    public void writeSurvey(Survey_Info Survey) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:8080/Survey/" + app.getGroupId();
        String json = makeSurveyJSON(Survey);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("JWT", app.getJWT())
                .build();

        try{
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeOption(Survey_Option Option) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:8080/Survey/" + app.getGroupId() + "/option";
        String json = makeOptionJSON(Option);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("JWT", app.getJWT())
                .build();

        try{
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeResult(Survey_Result Result) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:8080/Survey/" + app.getGroupId() + "/result";
        String json = makeResultJSON(Result);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("JWT", app.getJWT())
                .build();

        try{
            Response response = client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //POST 메소드에 전달할 게시글 정보를 JSON String 으로 변환
    public String makeJSONString(int BID, int isNotice, String title, String contents, int UID, int views_num) {

        String res = "{\"B_ID\":" + BID +
                ",\"isNotice\":" + isNotice +
                ",\"title\":\"" + title +
                "\",\"contents\":\"" + contents +
                "\",\"U_ID\":" + UID +
                ",\"views_num\":" + views_num +
                "}";

        return res;
    }
    //POST 메소드에 전달할 설문 정보를 JSON String으로 변환
    public String makeSurveyJSON(Survey_Info sInfo) {
        String res = "{\"SID\":" + sInfo.S_ID +
                ",\"TITLE\":\"" + sInfo.title +
                "\",\"BID\":" + sInfo.B_ID +
                "}";

        return res;
    }
    //POST 메소드에 전달할 옴션 정보를 JSON String으로 변환
    public String makeOptionJSON(Survey_Option sOpt) {
        String res = "{\"OID\":" + sOpt.O_ID +
                ",\"CONTENTS\":\"" + sOpt.contents +
                "\",\"SID\":" + sOpt.S_ID +
                "}";

        return res;
    }
    //POST 메소드에 전달할 결과 정보를 JSON String으로 변환
    public String makeResultJSON(Survey_Result sRes) {
        String res = "{\"OID\":" + sRes.O_ID +
                ",\"VOTED\":" + sRes.voted +
                "}";

        return res;
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

    //각각 다음 bid, sid, oid 읽어오는 callable 클래스

    private class nextBID implements Callable<Integer> {
        public Integer call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://10.0.2.2:8080/Community/" + app.getGroupId() + "/next";
            //빌드 후 ip 수정

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            int result = 0;

            try {
                Response response = client.newCall(request).execute();
                result = Integer.parseInt(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    private class nextSID implements Callable<Integer> {
        public Integer call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://10.0.2.2:8080/Survey/" + app.getGroupId();
            //빌드 후 ip 수정

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            int result = 0;

            try {
                Response response = client.newCall(request).execute();
                result = Integer.parseInt(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    private class nextOID implements Callable<Integer> {
        public Integer call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://10.0.2.2:8080/Survey/" + app.getGroupId() + "/oid";
            //빌드 후 ip 수정

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            int result = 0;

            try {
                Response response = client.newCall(request).execute();
                result = Integer.parseInt(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
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
                _v.setId(survey_ch);
                ((LinearLayout)findViewById(R.id.survey_list_container)).addView(_v);
                ((TextView)_v.findViewById(R.id.survey_create_list_title)).setText("항목 "+(survey_ch++));
            }

            v.findViewById(R.id.survey_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View _v = inflater.inflate(R.layout.survey_create_list, null);
                    _v.setId(survey_ch);
                    ((LinearLayout)findViewById(R.id.survey_list_container)).addView(_v);
                    ((TextView)_v.findViewById(R.id.survey_create_list_title)).setText("항목 "+(survey_ch++));
                }
            });
        }
    }

    //설문 등록 버튼 OnClick()
    public void createSurvey(View v){
        //v = 설문 등록 버튼
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        nextBID nextBID = new nextBID();
        nextSID nextSID = new nextSID();
        nextOID nextOID = new nextOID();

        Future<Integer> futureBID = executorService.submit(nextBID);
        Future<Integer> futureSID = executorService.submit(nextSID);
        Future<Integer> futureOID = executorService.submit(nextOID);

        int bid = 0;
        int sid = 0;
        int oid = 0;

        try {
            bid = futureBID.get();
            sid = futureSID.get();
            oid = futureOID.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nextbid = bid;


        for(int id = 0; id < survey_ch; id++) {
            View cont = findViewById(id);
            String option = ((TextView) cont.findViewById(R.id.option)).getText().toString();

            options.add(new Survey_Option(sid, oid, option));
            results.add(new Survey_Result(oid++, 0));
        }

        String title = ((EditText)findViewById(R.id.insert_title)).getText().toString();

        survey = new Survey_Info(sid, title, bid);
    }

    public void goBoardWriting(View view){
        Intent intent = new Intent(this, Board_Writing.class);
        startActivity(intent);
    }
}