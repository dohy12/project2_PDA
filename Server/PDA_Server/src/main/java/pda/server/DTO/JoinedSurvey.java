package pda.server.DTO;

import java.io.Serializable;

public class JoinedSurvey {
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

    public int getS_ID() { return S_ID; }
    public void setS_ID(int S_ID) { this.S_ID = S_ID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getB_ID() { return B_ID; }
    public void setB_ID(int S_ID) { this.B_ID = B_ID; }

    public int getO_ID() { return O_ID; }
    public void setO_ID(int S_ID) { this.O_ID = O_ID; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public int getVoted() { return voted; }
    public void setVoted(int voted) { this.voted = voted; }

}
