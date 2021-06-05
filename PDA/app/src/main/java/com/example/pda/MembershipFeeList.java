package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Serializable;
import java.security.acl.Group;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        Intent intent = getIntent();
        List<DueInfos> dueInfos = (List<DueInfos>) intent.getSerializableExtra("dueInfos");

        for(int i=0; i<dueInfos.size(); i++) {
            String start_date = dueInfos.get(i).get_start_date();
            String end_date = dueInfos.get(i).get_end_date();
            String valid_date = start_date + "~" + end_date;

            String title = dueInfos.get(i).get_title();
            int pay = dueInfos.get(i).get_pay();
            boolean is_payed = dueInfos.get(i).user_payed;
            boolean is_valid = dueInfos.get(i).is_valid;

            Fee fee = new Fee(is_payed, is_valid, title, valid_date, pay);
            fee.P_ID = dueInfos.get(i).get_PID();
            testList.add(fee);
        }

        /*
        testList.add(new Fee(true, "ㅇㅇ회비1", LocalDate.of(2021,5,25),30000));
        testList.add(new Fee(false, "ㅇㅇ회비2", LocalDate.of(2021,5,26),40000));
        testList.add(new Fee(false, "ㅇㅇ회비3", LocalDate.of(2021,5,27),60000));*/

        showList();
    }

    private class PostRequestRunnable implements Runnable {
        RetrofitService service;
        private String JWT;
        private PayReqInfos payReqInfos;
        public PostRequestRunnable(RetrofitService service, PayReqInfos payReqInfos, String JWT) {
            this.service = service;
            this.payReqInfos = payReqInfos;
            this.JWT = JWT;
        }
        public void run() {
            Call<String> call = service.PostRequestInfos(payReqInfos, JWT);
            try {
                String result = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showList(){
        for(int i=0;i<testList.size();i++) {
            View v = inflater.inflate(R.layout.fee_list, null);
            container.addView(v, 0);

            final Fee f = testList.get(i);
            ((TextView)v.findViewById(R.id.fee_title)).setText(f.getFeeTitle());

            ((TextView)v.findViewById(R.id.fee_date)).setText(f.getFeeDate());

            ((TextView)v.findViewById(R.id.fee_amount)).setText(String.format("%,d",f.getFeeAmount()));

            if(f.isPayed()){
                //v.findViewById(R.id.fee_border).setBackground(getResources().getDrawable(R.drawable.border_disabled));

                TextView tv = findViewById(R.id.fee_text);
                tv.setText("납부 완료");
                tv.setBackgroundColor(Color.parseColor("#909090"));
                tv.setTextColor(Color.parseColor("#FFFFFF"));

                ((TextView)v.findViewById(R.id.fee_amount)).setTextColor(Color.parseColor("#909090"));
            }
            else {
                if (f.isValid()) {
                    final Intent payment = new Intent(this, Payment.class);
                    ///회비 리스트에 onclickListener 추가
                    v.findViewById(R.id.fee_border);
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) { //결제 버튼 클릭 시
                            //결제 요청정보 전달
                            String origin = "http://18.206.18.154:8080/";
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(origin)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            RetrofitService service1 = retrofit.create(RetrofitService.class);

                            PayReqInfos payReqInfos = new PayReqInfos(app.getGroupId(), f.getP_ID(), app.getName());

                            Runnable r1 = new PostRequestRunnable(service1, payReqInfos, app.getJWT());
                            Thread thread1 = new Thread(r1);

                            thread1.start();

                            try {
                                thread1.join();
                            } catch (Exception e) { }

                            //결제 액티비티 시작
                            payment.putExtra("P_ID", f.getP_ID());
                            startActivity(payment);
                        }
                    });
                }
                else {
                    TextView tv = findViewById(R.id.fee_text);
                    tv.setText("미납-기간만료");
                    tv.setBackgroundColor(Color.parseColor("#909090"));
                    tv.setTextColor(Color.parseColor("#FFFFFF"));

                    ((TextView)v.findViewById(R.id.fee_amount)).setTextColor(Color.parseColor("#909090"));
                }
            }

        }
    }
}