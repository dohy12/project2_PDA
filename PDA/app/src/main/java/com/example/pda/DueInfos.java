package com.example.pda;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DueInfos implements Serializable { //intent간 객체 전달을 위한 Serializable 상속
    @SerializedName("start_date")
    private String start_date;

    @SerializedName("end_date")
    private String end_date;
    // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

    @SerializedName("title")
    private String title;
    // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑시켜줌

    @SerializedName("contents")
    private String contents;

    @SerializedName("P_ID")
    private int P_ID;

    @SerializedName("pay")
    private int pay;

    public boolean user_payed;

    public String get_start_date() {
        return start_date;
    }
    public String get_end_date() {
        return end_date;
    }
    public String get_title() {
        return title;
    }
    public int get_PID() {
        return P_ID;
    }
    public int get_pay() {
        return pay;
    }
    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "DueInfos{" +
                "start_date=" + start_date +
                ", end_date=" + end_date +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", P_ID='" + P_ID + '\'' +
                ", pay='" + pay + '\'' +
                '}';
    }
}
