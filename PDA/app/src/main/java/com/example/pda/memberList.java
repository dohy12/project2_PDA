package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class memberList extends AppCompatActivity {
    ArrayList<Member> memList;
    ArrayList<Member> memList_new;
    private LinearLayout container;
    private LinearLayout container_new;
    private LayoutInflater inflater;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        findViewById(R.id.memberList_profileImage).setClipToOutline(true);

        container = (LinearLayout)findViewById(R.id.memberList_container);
        container_new = (LinearLayout)findViewById(R.id.memberList_container_new);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        memList_new = new ArrayList<>();
        memList_new.add(new Member(1,"이도희0",27,"010-2890-6812","dohy12@naver.com","경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다."));

        memList = new ArrayList<>();
        memList.add(new Member(2,"이도희1",27,"010-2890-6812","dohy12@naver.com","경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다."));
        memList.add(new Member(3,"이도희2",26,"010-2890-6812","dohy12@naver.com","경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다.1"));
        memList.add(new Member(4,"이도희3",25,"010-2890-6812","dohy12@naver.com","경북대학교를 다니고 있는 학생입니다 잘 부탁드립니다.2"));

        showList();
    }

    private View setMemView(Member mem, LinearLayout _container){
        TextView tv;

        View v = inflater.inflate(R.layout.memberlist_member, null);
        _container.addView(v);

        v.findViewById(R.id.profile_image).setClipToOutline(true);

        ((TextView)v.findViewById(R.id.memList_id)).setText(Integer.toString(mem.getId()));

        String str_name_age = mem.getName() + "(" + mem.getAge() +")";
        tv = v.findViewById(R.id.memList_name);
        tv.setText(str_name_age);

        return v;
    }


    private void showList(){
        TextView tv;
        LinearLayout l;

        tv = (TextView) findViewById(R.id.memList_new_count);
        tv.setText("승인 대기 중인 인원(" + memList_new.size() + ")");
        for(int i=0;i<memList_new.size();i++){
            Member mem = memList_new.get(i);

            View v = setMemView(mem, container_new);

            v.findViewById(R.id.memList_btn).setVisibility(View.VISIBLE);
            l = v.findViewById(R.id.memList_back);
            l.setBackground(ContextCompat.getDrawable(this, R.drawable.border_new));
        }

        for(int i=0;i<memList.size();i++){

            Member mem = memList.get(i);

            View v = setMemView(mem, container);

            v.findViewById(R.id.memList_btn).setVisibility(View.GONE);
            l = v.findViewById(R.id.memList_back);

            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goMyPage(view);
                }
            });

        }

    }

    public void hide_new(View view){

        LinearLayout l;
        l = (LinearLayout) findViewById(R.id.memberList_container_new);

        if (l.getVisibility() == View.VISIBLE){
            l.setVisibility(View.GONE);
        }
        else{
            l.setVisibility(View.VISIBLE);
        }
    }

    public void goMyPage(View view){
        Intent intent = new Intent(this, Mypage.class);
        startActivity(intent);
    }

}