package com.example.pda;

import java.io.Serializable;

public class JoinedSurvey implements Serializable {
    int S_ID;
    String title;
    int B_ID;
    int O_ID;
    String contents;
    int voted;

    public JoinedSurvey(int S_ID, String title, int B_ID, int O_ID, String contents, int voted) {
        this.S_ID = S_ID;
        this.title = title;
        this.B_ID = B_ID;
        this.O_ID = O_ID;
        this.contents = contents;
        this.voted = voted;
    }
}