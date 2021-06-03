package com.example.pda;

import com.google.gson.annotations.SerializedName;

public class PayInfos {
    @SerializedName("pay")
    private int pay;

    @SerializedName("title")
    private String title;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    @SerializedName("buyer_name")
    private String buyer_name;

    @SerializedName("time")
    private String time;

    public int get_pay() {
        return pay;
    }
    public String get_title() {
        return title;
    }
    public String get_buyer_name() {
        return buyer_name;
    }
    public String get_time() {
        return time;
    }

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "PayInfos{" +
                "pay=" + pay +
                ", title=" + title +
                ", buyer_name='" + buyer_name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
