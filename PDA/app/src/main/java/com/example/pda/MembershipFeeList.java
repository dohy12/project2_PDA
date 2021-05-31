package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MembershipFeeList extends AppCompatActivity {
    private LayoutInflater inflater;
    private LinearLayout container;

    Toolbar toolbar;

    ArrayList<Fee> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_fee_list);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        container = findViewById(R.id.container);

        testList = new ArrayList<>();
        testList.add(new Fee(true,  "ㅇㅇ회비1", LocalDate.of(2021,5,25),30000));
        testList.add(new Fee(false, "ㅇㅇ회비2", LocalDate.of(2021,5,26),40000));
        testList.add(new Fee(false, "ㅇㅇ회비3", LocalDate.of(2021,5,27),60000));

        showList();
    }


    private void showList(){
        for(int i=0;i<testList.size();i++) {
            View v = inflater.inflate(R.layout.fee_list, null);
            container.addView(v, 0);

            Fee f = testList.get(i);
            ((TextView)v.findViewById(R.id.fee_title)).setText(f.getFeeTitle());

            String formatStr = f.getFeeDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            ((TextView)v.findViewById(R.id.fee_date)).setText(formatStr);

            ((TextView)v.findViewById(R.id.fee_amount)).setText(String.format("%,d",f.getFeeAmount()));

            if(f.isPayed()){
                v.findViewById(R.id.fee_border).setBackground(getResources().getDrawable(R.drawable.border_disabled));

                TextView tv = findViewById(R.id.fee_text);
                tv.setText("납부 완료");
                tv.setBackgroundColor(Color.parseColor("#909090"));
                tv.setTextColor(Color.parseColor("#FFFFFF"));

                ((TextView)v.findViewById(R.id.fee_amount)).setTextColor(Color.parseColor("#909090"));

            }
        }
    }
}