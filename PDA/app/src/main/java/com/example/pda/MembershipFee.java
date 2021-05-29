package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MembershipFee extends AppCompatActivity {
    private LayoutInflater inflater;
    private LinearLayout container;

    ArrayList<FeeUsage> tempList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_fee);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2);
        ////////////////////////

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        container = findViewById(R.id.container);

        tempList = new ArrayList<>();
        tempList.add(new FeeUsage(LocalDate.of(2021,5,25),"운동회 행사", 30000, 1000000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,26),"운동회 행사2", 30000, 970000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,27),"운동회 행사3", 50000, 920000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,28),"운동회 행사4", 30000, 890000));

        showList();
    }


    private void showList(){
        for(int i=0;i<tempList.size();i++) {
            View v = inflater.inflate(R.layout.fee_usage_list, null);
            container.addView(v, 0);

            FeeUsage f = tempList.get(i);
            String formatStr = f.getUseDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
            ((TextView)v.findViewById(R.id.fee_date)).setText(formatStr);

            ((TextView)v.findViewById(R.id.fee_detail)).setText(f.getDetail());
            ((TextView)v.findViewById(R.id.fee_amount)).setText(String.format("-%,d",f.getAmount()));
            ((TextView)v.findViewById(R.id.fee_remained)).setText(String.format("남은 회비 %,d",f.getRemained()));
        }
    }

}