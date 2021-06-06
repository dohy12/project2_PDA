package com.example.pda;

public class Survey_Info {
    int S_ID;
    String title;
    int B_ID;

    public Survey_Info(int S_ID, String title, int B_ID) {
        this.S_ID = S_ID;
        this.title = title;
        this.B_ID = B_ID;
    }

    public int getS_ID() { return S_ID; }

    public String getTitle() { return title; }

    public int getB_ID() { return B_ID; }
}
