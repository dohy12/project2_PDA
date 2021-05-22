package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Board_content extends AppCompatActivity {
    private LinearLayout comments_container;
    private LinearLayout survey_container;
    private LinearLayout image_container;
    private LayoutInflater inflater;
    ArrayList<Drawable> imageList;
    ArrayList<Board_comment> boardCommentList;
    Board_Info boardInfo;

    Survey survey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        comments_container = findViewById(R.id.board_comments_container);
        survey_container = findViewById(R.id.board_survey_container);
        image_container = findViewById(R.id.board_image_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        boardInfo = new Board_Info(1 , true, "제목", "이도희", LocalDateTime.of(21,5,22,12,46),222, 5, "내용");

        imageList = new ArrayList<>();
        imageList.add(getResources().getDrawable(R.drawable.img1, null));
        imageList.add(getResources().getDrawable(R.drawable.img5, null));

        boardCommentList = new ArrayList<>();
        boardCommentList.add(new Board_comment(5,-1,"도희","댓글내용",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(6,-1,"도희1","댓글내용2",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(7,5,"도희2","답글내용1",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(9,5,"도희2","답글내용2",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(10,-1,"도희3","댓글내용3",LocalDateTime.of(21,5,22,12,56)));

        String[] survey_strList = {"항목A", "항목B", "항목C"};
        int[] survey_countList = {5, 1, 2};
        survey = new Survey(-1, "설문조사 제목", survey_strList, survey_countList);

        showBoardInfo();
        showSurvey();
        showImageList();
        showCommentList();
    }

    private void showBoardInfo(){ // 게시판 내용 넣기
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
        for(int i=0;i<imageList.size();i++) {
            View v = inflater.inflate(R.layout.board_image, null);
            image_container.addView(v);

            ((ImageView)v.findViewById(R.id.board_image)).setImageDrawable(imageList.get(i));
        }
    }

    private void showSurvey(){
        if(survey!=null){
            survey_container.setVisibility(View.VISIBLE);
            View v = inflater.inflate(R.layout.survey, null);
            survey_container.addView(v);

            ((TextView)v.findViewById(R.id.survey_title)).setText("[설문조사] "+survey.getSurveyTitle());

            int count = survey.getSurveyList().length;
            RadioGroup radioGroup = findViewById(R.id.survey_radioGroup);
            LinearLayout surveyList_container = findViewById(R.id.survey_list_container);

            if(survey.getSelect()==-1){  // 항목이 선택되지 않았을 경우
                radioGroup.setVisibility(View.VISIBLE);
                surveyList_container.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.survey_button)).setText("투표하기");

                for(int i=0;i<count;i++){
                    RadioButton btn = new RadioButton(this);

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
                for(int i=0;i<count;i++)
                    max_count = Math.max(max_count, survey.getSurveyList_count()[i]);

                for(int i=0;i<count;i++){
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

    private void showCommentList(){
        for(int i=0;i<boardCommentList.size();i++) {
            Board_comment bc = boardCommentList.get(i);
            View v = inflater.inflate(R.layout.board_comments, null);

            ((TextView)v.findViewById(R.id.comments_id)).setText(Integer.toString(bc.getCommentID()));
            ((TextView)v.findViewById(R.id.comments_name)).setText(bc.getName());
            ((TextView)v.findViewById(R.id.comments_contents)).setText(bc.getComments());

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
        }
    }


}