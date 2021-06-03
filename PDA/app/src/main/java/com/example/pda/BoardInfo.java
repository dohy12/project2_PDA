package com.example.pda;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class BoardInfo {
    @SerializedName("B_ID")
    private int B_ID;

    @SerializedName("isNotice")
    public int isNotice;

    @SerializedName("title")
    public String title;

    @SerializedName("date")
    public Timestamp date;

    @SerializedName("contents")
    public String contents;

    @SerializedName("U_ID")
    public int U_ID;

    @Override
    public String toString() {
        return "BoardInfo{" +
                "B_ID=" + B_ID +
                ", isNotice=" + isNotice +
                ", title='" + title + "\'" +
                ", date='" + date + "\'" +
                ", contents='" + contents + "\'" +
                ", U_ID=" + U_ID +
                "}";
    }
}
