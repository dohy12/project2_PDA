package com.example.pda;

public class Survey_Option {
    int S_ID;
    int O_ID;
    String contents;

    public Survey_Option(int S_ID, int O_ID, String contents) {
        this.S_ID = S_ID;
        this.O_ID = O_ID;
        this.contents = contents;
    }

    public int getS_ID() { return S_ID; }
    public int getO_ID() { return O_ID; }
    public String getContents() { return contents; }
}
