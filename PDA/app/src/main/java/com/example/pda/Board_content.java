package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Board_content extends AppCompatActivity {
    private LinearLayout comments_container;
    private LinearLayout survey_container;
    private LinearLayout image_container;
    private LayoutInflater inflater;
    ArrayList<Board_comment> boardCommentList;
    ArrayList<JoinedSurvey> joinedSurveyList;
    ArrayList<Board_Image> boardImageList;
    Board_Info boardInfo;
    int cid = -1;
    Survey survey = null;
    String[] survey_strList = new String[5];
    int[] survey_countList = new int[5];
    int size = 0;

    Toolbar toolbar;

    final Handler rehandler = new Handler(){
        public void handleMessage(Message msg){
            reloadComments(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        comments_container = findViewById(R.id.board_comments_container);
        survey_container = findViewById(R.id.board_survey_container);
        image_container = findViewById(R.id.board_image_container);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        comments_container.removeAllViews();


        //Board에서 인자로 넘겨준 Board_Info 객체 받는 부분
        Intent myIntent = getIntent();
        boardInfo = (Board_Info) myIntent.getSerializableExtra("selectedBoard");

        //조회 됐을 때 views_num을 +1 시켜주는 부분
        updateViewsNum(boardInfo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        isExist isExist = new isExist();
        imageExist imageExist = new imageExist();
        Future<Integer> futureExist = executorService.submit(isExist);
        Future<Integer> futureImg = executorService.submit(imageExist);
        int exist = 0;
        int img = 0;
        try {
            exist = futureExist.get();
            img = futureImg.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(exist > 0) {
            SurveyCallable SurveyCallable = new SurveyCallable();
            Future<ArrayList<JoinedSurvey>> future = executorService.submit(SurveyCallable);

            try {
                joinedSurveyList = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < joinedSurveyList.size(); i++) {
                survey_strList[i] = joinedSurveyList.get(i).contents;
                survey_countList[i] = joinedSurveyList.get(i).voted;
            }
            survey = new Survey(-1, joinedSurveyList.get(0).title, survey_strList, survey_countList);
            size = joinedSurveyList.size();
        }

        showBoardInfo();
        if(exist > 0)
            showSurvey();
        if(img > 0)
            showImageList();
        showCommentList();
    }

    //PUT 메소드 부르는 http 구문
    //조회수 +1
    public void updateViewsNum(Board_Info board) {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create("body", null);

        Request request = new Request.Builder()
                .url("http://" + app.getHostip() + ":8080/Community/" + app.getGroupId() + "/views/" + board.getBoardId())
                .addHeader("JWT", app.getJWT())
                .put(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class isExist implements Callable<Integer> {
        public Integer call() {
            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/Survey/";
            String GroupId = app.getGroupId();
            int bid = boardInfo.getBoardId();

            String httpUrl = url + GroupId + "/exist/" + bid;

            Request request = new Request.Builder()
                    .url(httpUrl)
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

    private class imageExist implements Callable<Integer> {
        public Integer call() {
            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/BoardImage/";
            String GroupId = app.getGroupId();
            int bid = boardInfo.getBoardId();

            String httpUrl = url + GroupId + "/" + bid;

            Request request = new Request.Builder()
                    .url(httpUrl)
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

    private class SurveyCallable implements Callable<ArrayList<JoinedSurvey>> {
        public ArrayList<JoinedSurvey> call() {
            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/Survey/";
            String GroupId = app.getGroupId();
            int bid = boardInfo.getBoardId();

            String httpUrl = url + GroupId + "/" + bid;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            try {
                Response response = client.newCall(request).execute();
                JSONArray jsonArray = new JSONArray(response.body().string());
                joinedSurveyList = new ArrayList<JoinedSurvey>();

                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    System.out.println(jsonObject.toString());

                    int SID = jsonObject.getInt("s_ID");
                    String title = jsonObject.getString("title");
                    int BID = jsonObject.getInt("b_ID");
                    int OID = jsonObject.getInt("o_ID");
                    String contents = jsonObject.getString("contents");
                    int voted = jsonObject.getInt("voted");

                    joinedSurveyList.add(new JoinedSurvey(SID, title, BID, OID, contents, voted));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return joinedSurveyList;
        }
    }


    private class ImageCallable implements Callable<ArrayList<Board_Image>> {
        public ArrayList<Board_Image> call() {
            OkHttpClient client = new OkHttpClient();

            int bid = boardInfo.getBoardId();
            String url = "http://" + app.getHostip() + ":8080/BoardImage/" + app.getGroupId() + "/" + bid + "/show";

            System.out.println(url);

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            try {
                Response response = client.newCall(request).execute();

                JSONArray jsonArray = new JSONArray(response.body().string());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int IID = jsonObject.getInt("i_ID");
                    String image_src = jsonObject.getString("imageSrc");
                    int BID = jsonObject.getInt("b_ID");

                    boardImageList.add(new Board_Image(IID, image_src, BID));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return boardImageList;
        }
    }

    private class CommentCallable implements Callable<ArrayList<Board_comment>> {
        public ArrayList<Board_comment> call() {
            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/BoardComment/";
            String GroupId = app.getGroupId();
            int bid = boardInfo.getBoardId();

            String httpUrl = url + GroupId + "/" + bid;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            try {
                Response response = client.newCall(request).execute();

                JSONArray jsonArray = new JSONArray(response.body().string());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int CID = jsonObject.getInt("C_ID");
                    String dateTemp = jsonObject.getString("dateTime");
                    LocalDateTime date = LocalDateTime.parse(dateTemp, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                    String contents = jsonObject.getString("contents");
                    int BID = jsonObject.getInt("B_ID");
                    int UID = jsonObject.getInt("U_ID");
                    int R_CID = jsonObject.getInt("R_CID");
                    String name = jsonObject.getString("name");

                    boardCommentList.add(new Board_comment(CID, R_CID, name, contents, date, UID));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return boardCommentList;
        }
    }

    private class BoardDeletion implements Callable<String> {
        public String call() {
            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/Community/";
            String groupId = app.getGroupId();
            int bid = boardInfo.getBoardId();

            String httpUrl = url + groupId + "/" + bid;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .delete()
                    .addHeader("JWT", app.getJWT())
                    .build();

            String result = null;

            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }



    }

    public void postOrPutBtn(View v) {
        String postORput = ((Button)v).getText().toString();

        if(postORput.equals("등록") == true) {
            writeComment(v);
            reloadComments(v);
        } else {
            updateComment(v, cid);
            ((Button)v).setText("등록");
        }
    }

    public void writeComment(View v) {
        String contents = ((EditText) findViewById(R.id.comment)).getText().toString();
        int uid = Integer.parseInt(app.getUid());

        OkHttpClient client = new OkHttpClient();

        String json = null;

        json = makeJSONString(0, contents, boardInfo.getBoardId(), uid, cid);

        System.out.println(json);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url("http://" + app.getHostip() + ":8080/BoardComment/" + app.getGroupId())
                .post(body)
                .addHeader("JWT", app.getJWT())
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            comments_container.removeAllViews();
            showCommentList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((EditText)findViewById(R.id.comment)).setText("");
        ((EditText)findViewById(R.id.comment)).setInputType(0);

        cid = -1;
    }

    public void updateComment(View v, int cid) {
        String contents = ((EditText) findViewById(R.id.comment)).getText().toString();
        int uid = Integer.parseInt(app.getUid());

        OkHttpClient client = new OkHttpClient();

        String json = makeJSONString(0, contents, boardInfo.getBoardId(), uid, -1);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url("http://" + app.getHostip() + ":8080/BoardComment/" + app.getGroupId() + "/" + cid)
                .put(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            comments_container.removeAllViews();
            showCommentList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((EditText)findViewById(R.id.comment)).setText("");
        ((EditText)findViewById(R.id.comment)).setInputType(0);

        cid = -1;

    }

    public void reloadComments(View view)
    {
        comments_container.removeAllViews();
        showCommentList();
    }

    private class CommentDeletion implements Callable<String> {
        private int cid;

        public CommentDeletion(int cid)
        {
            this.cid = cid;
        }

        public String call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/BoardComment/";
            //빌드 후 ip 수정
            String groupId = app.getGroupId();
            int uid = Integer.parseInt(app.getUid());

            String httpUrl = url + groupId + "/" + cid + "/" + uid;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .delete()
                    .addHeader("JWT", app.getJWT())
                    .build();

            String result = null;

            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
                rehandler.sendMessage(rehandler.obtainMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public String makeJSONString(int cid, String contents, int bid, int uid, int rcid) {
        String res = "{\"CID\":" + cid +
                ",\"contents\":\"" + contents +
                "\",\"BID\":" + bid +
                ",\"UID\":" + uid +
                ",\"RCID\":" + rcid +
                "}";

        return res;
    }

    private void showBoardInfo(){ // 게시판 내용 넣기

        String profile = null;
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        getURL getUrl = new getURL(boardInfo.getU_ID());
        Future<String> future2 = executorService2.submit(getUrl);

        try {
            profile = future2.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "http://" + app.getHostip() + ":8080/images/" + profile;

        Glide.with(this).load(url).into((ImageView)findViewById(R.id.profile_image));
        findViewById(R.id.profile_image).setClipToOutline(true);

        Boolean isNotice = boardInfo.getNotice();//공지 체크

        if(isNotice)
        {
            ((TextView)findViewById(R.id.board_title)).setTextColor(Color.rgb(255,0,0));
            ((TextView)findViewById(R.id.board_title)).setText("[공지] "+boardInfo.getTitle());
        }
        else
        {
            ((TextView)findViewById(R.id.board_title)).setText(boardInfo.getTitle());
        }

        ((TextView)findViewById(R.id.board_mem_name)).setText(boardInfo.getName());

        String formatDate = boardInfo.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        String str_boardInfo = formatDate + " 조회수 " + boardInfo.getViews_num();
        ((TextView)findViewById(R.id.board_info)).setText(str_boardInfo);

        ((TextView)findViewById(R.id.board_container)).setText(boardInfo.getContents());
    }

    private void showImageList(){ //이미지 추가
        boardImageList = new ArrayList<Board_Image>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ImageCallable imageCallable = new ImageCallable();
        Future<ArrayList<Board_Image>> future = executorService.submit(imageCallable);

        try {
            boardImageList = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0;i<boardImageList.size();i++) {
            View v = inflater.inflate(R.layout.board_image, null);
            image_container.addView(v);

            try {
                String url = "http://" + app.getHostip() + ":8080/images/" + boardImageList.get(i).image_src + ".png";
                Glide.with(this).load(url).into((ImageView) v.findViewById(R.id.board_image));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void showSurvey(){
        if(survey!=null){
            survey_container.setVisibility(View.VISIBLE);
            View v = inflater.inflate(R.layout.survey, null);
            survey_container.addView(v);

            ((TextView)v.findViewById(R.id.survey_title)).setText("[설문조사] "+survey.getSurveyTitle());

            RadioGroup radioGroup = findViewById(R.id.survey_radioGroup);
            LinearLayout surveyList_container = findViewById(R.id.survey_list_container);

            if(survey.getSelect()==-1){  // 항목이 선택되지 않았을 경우
                radioGroup.setVisibility(View.VISIBLE);
                surveyList_container.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.survey_button)).setText("투표하기");

                for(int i=0;i<size;i++){
                    RadioButton btn = new RadioButton(this);
                    btn.setId(i);

                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());

                    btn.setLayoutParams(param);
                    btn.setText("항목"+(i+1) + " : " +survey.getSurveyList()[i]);

                    radioGroup.addView(btn);
                }
            }
            else{// 항목이 선택됐을 경우
                radioGroup.setVisibility(View.GONE);
                surveyList_container.setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.survey_button)).setText("재 투표하기");

                int max_count = 0;
                for(int i=0;i<size;i++)
                    max_count = Math.max(max_count, survey.getSurveyList_count()[i]);

                for(int i=0;i<size;i++){
                    View _v = inflater.inflate(R.layout.survey_list, null);
                    surveyList_container.addView(_v);

                    ((TextView)_v.findViewById(R.id.survey_list_tile)).setText(survey.getSurveyList()[i]);

                    int surveyList_count = survey.getSurveyList_count()[i];
                    float surveyList_bar_len = (float)surveyList_count/(float)max_count * 9.0f;
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    param.weight = surveyList_bar_len;
                    _v.findViewById(R.id.survey_list_bar).setLayoutParams(param);

                    param = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                    param.weight = (10.0f-surveyList_bar_len);
                    _v.findViewById(R.id.survey_list_count).setLayoutParams(param);
                    ((TextView)_v.findViewById(R.id.survey_list_count)).setText(surveyList_count + "표");

                }
            }
        }
    }

    //투표 버튼 선택
    public void surveyOn(View v){
        RadioGroup rg = (RadioGroup) findViewById(R.id.survey_radioGroup);
        int selected = rg.getCheckedRadioButtonId();

        int oid = joinedSurveyList.get(selected).O_ID;


        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://" + app.getHostip() + ":8080/Survey/" + app.getGroupId() + "/voted/" + oid)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();

        try {
            client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        survey_countList[selected]++;
        survey.setSurveyList_count(survey_countList);

        survey.setSelect(oid);
        survey_container.removeAllViews();
        showSurvey();
    }

    private void showCommentList(){
        boardCommentList = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CommentCallable commentCallable = new CommentCallable();
        Future<ArrayList<Board_comment>> future = executorService.submit(commentCallable);

        try {
            boardCommentList = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0;i<boardCommentList.size();i++) {
            Board_comment bc = boardCommentList.get(i);
            View v = inflater.inflate(R.layout.board_comments, null);

            String profile = null;
            ExecutorService executorService2 = Executors.newSingleThreadExecutor();
            getURL getUrl = new getURL(bc.getUID());
            Future<String> future2 = executorService2.submit(getUrl);

            try {
                profile = future2.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "http://" + app.getHostip() + ":8080/images/" + profile;

            Glide.with(this).load(url).into((ImageView)v.findViewById(R.id.profile_image));
            v.findViewById(R.id.profile_image).setClipToOutline(true);

            ((TextView)v.findViewById(R.id.comments_id)).setText(Integer.toString(bc.getCommentID()));
            ((TextView)v.findViewById(R.id.comments_name)).setText(bc.getName());
            ((TextView)v.findViewById(R.id.comments_contents)).setText(bc.getComments());
            System.out.println(((TextView)v.findViewById(R.id.comments_id)).getText());
            String formatDate = bc.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            ((TextView)v.findViewById(R.id.comments_date)).setText(formatDate);

            int replyId = bc.getReplyID();
            if(replyId == -1)
                comments_container.addView(v);
            else
            {
                int count = comments_container.getChildCount();

                for(int j=0;j<count;j++)
                {
                    View childV = comments_container.getChildAt(j);

                    int comments_id = Integer.parseInt((String) ((TextView)childV.findViewById(R.id.comments_id)).getText());
                    if(replyId == comments_id)
                        ((LinearLayout)childV.findViewById(R.id.comments_reply_container)).addView(v);
                }
            }

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openCommentMenu(view);

                    return true;
                }
            });

        }

        ((TextView)findViewById(R.id.board_comments_num)).setText("댓글" + boardCommentList.size());
    }

    private class getURL implements Callable<String> {
        private int uid;

        public getURL(int uid)
        {
            this.uid = uid;
        }

        public String call() {

            OkHttpClient client = new OkHttpClient();

            String url = "http://" + app.getHostip() + ":8080/BoardComment/";
            int uid = Integer.parseInt(app.getUid());


            String httpUrl = url + uid;

            System.out.println(httpUrl);

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .get()
                    .addHeader("JWT", app.getJWT())
                    .build();

            String result = null;

            try {
                Response response = client.newCall(request).execute();
                result = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public void goBoardWriting(View view){
        LocalDateTime date = LocalDateTime.now();
        Board_Info fake = new Board_Info(0,false, "", "", date, 0, 0, Integer.parseInt(app.getUid()));
        Intent intent = new Intent(this, Board_Writing.class);
        intent.putExtra("selectedBoard", fake);
        startActivity(intent);
    }

    public void openMenu(View view){
        View anchor = findViewById(R.id.menu_anchor);
        final PopupMenu popupMenu = new PopupMenu(this, anchor);
        getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());

        Menu menu = popupMenu.getMenu();

        menu.add(Menu.NONE, 0, Menu.NONE, "수정");
        menu.add(Menu.NONE, 1, Menu.NONE, "삭제");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i= menuItem.getItemId();

                switch(i){
                    case 1:
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        BoardDeletion boardDeletion = new BoardDeletion();
                        Future<String> future = executorService.submit(boardDeletion);

                        String del = null;

                        try {
                            del = future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(toolbar.getActivity(), del, Toast.LENGTH_SHORT).show();
                        System.out.println(del);

                        finish();

                        break;

                    case 0:
                        //작성자 동일할 때 수정 가능
                        if(app.getName().equals(boardInfo.getName())) {

                            //새로운 Activity Board_Modify 열어야 함
                            Toast.makeText(toolbar.getActivity(), "수정", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(toolbar.getActivity(), Board_Writing.class);
                            intent.putExtra("selectedBoard", boardInfo);
                            startActivity(intent);
                        } else {
                            Toast.makeText(toolbar.getActivity(), app.getName() + boardInfo.getName() + "수정 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                        }

                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void openCommentMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());
        cid = Integer.parseInt(((TextView)view.findViewById(R.id.comments_id)).getText().toString());
        Menu menu = popupMenu.getMenu();

        menu.add(Menu.NONE, 0, Menu.NONE, "수정");
        menu.add(Menu.NONE, 1, Menu.NONE, "삭제");
        menu.add(Menu.NONE, 2, Menu.NONE, "대댓글");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int i= menuItem.getItemId();

                switch(i){
                    case 1:
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        CommentDeletion commentDeletion = new CommentDeletion(cid);
                        Future<String> future = executorService.submit(commentDeletion);

                        String del = null;

                        try {
                            del = future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(toolbar.getActivity(), del, Toast.LENGTH_SHORT).show();

                        cid = -1;
                        break;
                    case 0:
                        ((Button)findViewById(R.id.postORput)).setText("수정");
                        Toast.makeText(toolbar.getActivity(), "수정", Toast.LENGTH_SHORT).show();
                        String contents = null;
                        for(int iter = 0; i < boardCommentList.size(); i++) {
                            Board_comment comm = boardCommentList.get(iter);
                            if (comm.getCommentID() == cid)
                                contents = comm.getComments();
                        }
                        ((EditText)findViewById(R.id.comment)).setText(contents);
                        break;
                    case 2:
                        Toast.makeText(toolbar.getActivity(), "대댓글", Toast.LENGTH_SHORT).show();
                        ((EditText)findViewById(R.id.comment)).requestFocus();
                }
                return false;
            }
        });
        popupMenu.show();
    }

}