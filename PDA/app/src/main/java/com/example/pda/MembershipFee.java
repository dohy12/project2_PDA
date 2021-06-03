package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.Group;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MembershipFee extends AppCompatActivity {
    private LayoutInflater inflater;
    private LinearLayout container;
    private int total = 0;
    private int toPay = 0;
    private List<DueInfos> dueInfos;
    private List<PayInfos> payInfos;
    private List<Integer> userdueInfos;
    private String GroupId, JWT;
    ArrayList<FeeUsage> tempList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_fee);

        ///툴바 세팅/////////////
        toolbar = new Toolbar(findViewById(R.id.toolbar), null, 2, this);
        ////////////////////////

        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        container = findViewById(R.id.container);

        tempList = new ArrayList<>();

        String origin = "http://8fae9f085367.ngrok.io/";
        GroupId = "deaa01013b0144e99faab90ecd670950";
        JWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTAzMjM4NTQ5IiwiZXhwIjoxNjIyNjk1NzAzLCJpYXQiOjE2MjI2OTM5MDN9.Sj0boAjOB7AQ0d_b7tpHo5ETarDh7fghlA7piImRJQvfHVaWSIQA8IO4OzBIvBk9Irq2bv4rycNZdHlwsIcS0g";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(origin)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<List<PayInfos>> call1 = service1.getPayInfos(GroupId, JWT);

        call1.enqueue(new Callback<List<PayInfos>>() { //비동기
            @Override
            public void onResponse(Call<List<PayInfos>> call, Response<List<PayInfos>> response) {
                if(response.isSuccessful()) {
                    payInfos = response.body();
                    String infos = payInfos.toString();
                    Log.i("retro result : " , infos);

                    for (int i=0; i<payInfos.size(); i++) {
                        SimpleDateFormat df1 = new SimpleDateFormat("yymmdd");
                        SimpleDateFormat df2 = new SimpleDateFormat("yy.mm.dd");
                        Date format_time;
                        String time;
                        try {
                            format_time = df1.parse(payInfos.get(i).get_time());
                            time = df2.format(format_time);

                            int pay = payInfos.get(i).get_pay();
                            String title = payInfos.get(i).get_title();
                            String buyer_name = payInfos.get(i).get_buyer_name();
                            String contents = buyer_name + " " + title + " 납부";

                            total = total + pay;
                            tempList.add(new FeeUsage(time, contents, pay, total));
                        }
                        catch(Exception ParseException) {
                            System.out.println("parsing error");
                        }
                    }
                    showList();
                } else {
                    Log.i("error", "retro failed");
                }
            }

            @Override
            public void onFailure(Call<List<PayInfos>> call, Throwable t) {
                Log.i("error", "retro failed");
            }
        });

        //동기 쓰레드
        Runnable r1 = new DueInfosRunnable(service1, GroupId, JWT);
        Thread thread1 = new Thread(r1);
        Runnable r2 = new UserDueInfosRunnable(service1, GroupId, JWT);
        Thread thread2 = new Thread(r2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {
        }

        for(int i=0; i<dueInfos.size(); i++) {
            dueInfos.get(i).user_payed = true;
        }

        for(int i=0; i<dueInfos.size(); i++) {
            for(int j=0; j<userdueInfos.size(); j++) {
                if(dueInfos.get(i).get_PID() != userdueInfos.get(j)) { //유저가 낸 회비가 아니라면
                    //날짜
                    try {
                        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-mm-dd");
                        Date now = new Date();
                        String now_date = df1.format(now);

                        Date format_now = df1.parse(now_date);
                        Date format_start_date = df1.parse(dueInfos.get(i).get_start_date());
                        Date format_end_date = df1.parse(dueInfos.get(i).get_end_date());

                        int compare1 = format_now.compareTo(format_start_date);
                        if(compare1 >= 0) { //now >= start_date //현재 날짜가 유효 기간 내라면
                            int compare2 = format_now.compareTo(format_end_date);
                            if(compare2 <= 0) {
                                toPay += dueInfos.get(i).get_pay();
                                dueInfos.get(i).user_payed = false; //PAY 해야함
                                //Log.i("P_ID", Integer.toString(dueInfos.get(i).get_PID()));
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        ((TextView)findViewById(R.id.toPay)).setText(String.format("%,d원", toPay));
        /*
        tempList.add(new FeeUsage(LocalDate.of(2021,5,25),"운동회 행사", -30000, 1000000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,26),"운동회 행사2", -30000, 970000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,27),"운동회 행사3", -50000, 920000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,28),"운동회 행사4", -30000, 890000));
        tempList.add(new FeeUsage(LocalDate.of(2021,5,29),"(이도희) 회비 납부", 100000, 990000));

        showList();*/
    }

    private class DueInfosRunnable implements Runnable {
        RetrofitService service;
        private String GroupId, JWT;
        public DueInfosRunnable(RetrofitService service, String GroupId, String JWT) {
            this.service = service;
            this.GroupId = GroupId;
            this.JWT = JWT;
        }
        public void run() {
            Call<List<DueInfos>> call = service.getDueInfos(GroupId, JWT);
            try {
                dueInfos = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UserDueInfosRunnable implements Runnable {
        RetrofitService service;
        private String GroupId, JWT;
        public UserDueInfosRunnable(RetrofitService service, String GroupId, String JWT) {
            this.service = service;
            this.GroupId = GroupId;
            this.JWT = JWT;
        }
        public void run() {
            Call<List<Integer>> call = service.getUserDueInfos(GroupId, JWT);
            try {
                userdueInfos = call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showList(){
        for(int i=0;i<tempList.size();i++) {
            View v = inflater.inflate(R.layout.fee_usage_list, null);
            container.addView(v, 0);

            //납부된 회비
            ((TextView)findViewById(R.id.fee_total)).setText(String.format("%,d원", total));

            FeeUsage f = tempList.get(i);
            String formatStr = f.getValidDate();
            ((TextView)v.findViewById(R.id.fee_date)).setText(formatStr);

            ((TextView)v.findViewById(R.id.fee_detail)).setText(f.getDetail());
            ((TextView)v.findViewById(R.id.fee_amount)).setText(String.format("%,d원",f.getAmount()));
            ((TextView)v.findViewById(R.id.fee_remained)).setText(String.format("남은 회비 %,d원",f.getRemained()));

            //// 사용한 회비: 빨간색   회비 납부 : 파란색
            if(f.getAmount()>0){
                ((TextView)v.findViewById(R.id.fee_amount)).setTextColor(Color.parseColor("#0070c0"));
            }
            else{
                ((TextView)v.findViewById(R.id.fee_amount)).setTextColor(Color.parseColor("#c00000"));
            }
        }
    }

    public void goMembershipFeeList(View view){
        Intent intent = new Intent(this, MembershipFeeList.class); //dueInfos 객체 전달 해야함
        intent.putExtra("dueInfos", (Serializable) dueInfos);
        startActivity(intent);
    }
}