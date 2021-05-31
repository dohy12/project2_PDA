package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Mypage extends AppCompatActivity {
    private LinearLayout container;
    private LayoutInflater inflater;
    Member mem;
    ArrayList<GuestBook> guestBookList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        container = (LinearLayout)findViewById(R.id.guestBook_container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        guestBookList = new ArrayList<>();
        guestBookList.add(new GuestBook("이도희","안녕하세요",LocalDateTime.of(2021,5,15,9,51)));
        guestBookList.add(new GuestBook("이도희2","안녕하세요",LocalDateTime.of(2021,5,16,9,51)));
        guestBookList.add(new GuestBook("이도희3","안녕하세요",LocalDateTime.of(2021,5,17,9,51)));
        guestBookList.add(new GuestBook("이도희4","안녕하세요",LocalDateTime.of(2021,5,18,9,51)));

        mem = new Member(1,"이도희1",27,"010-2890-6812","dohy12@naver.com","경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다.");
        setMypage(mem);
        showBookList();
    }

    private void setMypage(Member mem){
        findViewById(R.id.mypage_profileImage).setClipToOutline(true);

        String str_name_age = mem.getName() + "(" + mem.getAge() +")";
        ((TextView)findViewById(R.id.memList_name)).setText(str_name_age);

        String str_phone = "Mobile. " + mem.getPhone();
        ((TextView)findViewById(R.id.memList_phone)).setText(str_phone);

        String str_email = "E-mail. " + mem.getEmail();
        ((TextView)findViewById(R.id.memList_email)).setText(str_email);
        ((TextView)findViewById(R.id.memList_intro)).setText(mem.getIntro());
    }

    private void showBookList(){
        ((TextView)findViewById(R.id.guestBook_num)).setText("방명록("+guestBookList.size()+"개)");
        for(int i=0;i<guestBookList.size();i++)
        {
            GuestBook guestBook = guestBookList.get(i);
            View v = inflater.inflate(R.layout.guestbook, null);
            container.addView(v);

            v.findViewById(R.id.profile_image).setClipToOutline(true);

            ((TextView)v.findViewById(R.id.guestBook_name)).setText(guestBook.getName());
            ((TextView)v.findViewById(R.id.guestBook_comment)).setText(guestBook.getComment());

            String formatDate = guestBook.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            ((TextView)v.findViewById(R.id.guestBook_date)).setText(formatDate);
        }
    }


}