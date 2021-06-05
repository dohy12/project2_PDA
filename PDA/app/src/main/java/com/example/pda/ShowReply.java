package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class ShowReply extends AppCompatActivity {
    private LayoutInflater inflater;
    private LinearLayout container;
    private ArrayList<Board_comment> boardCommentList;
    private ReplyList replyList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reply);


        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////


        container = (LinearLayout)findViewById(R.id.container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        boardCommentList = new ArrayList<>();
        boardCommentList.add(new Board_comment(5,-1,"도희","댓글내용", LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(6,-1,"도희1","댓글내용2",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(7,5,"도희2","답글내용1",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(9,5,"도희2","답글내용2",LocalDateTime.of(21,5,22,12,56)));
        boardCommentList.add(new Board_comment(10,-1,"도희3","댓글내용3",LocalDateTime.of(21,5,22,12,56)));

        replyList = new ReplyList(this, boardCommentList, container);

    }
}