package com.example.pda;

public class Survey_Result {
    int O_ID;
    int voted;

    public Survey_Result(int O_ID, int voted) {
        this.O_ID = O_ID;
        this.voted = voted;
    }

    public int getO_ID() { return O_ID; }
    public int getVoted() { return voted; }
}
