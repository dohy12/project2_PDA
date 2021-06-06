package pda.server.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Survey {
    int S_ID;
    String title;
    Timestamp start;
    Timestamp end;
    int B_ID;
    int O_ID;
    String contents;
    int voted;

    public Survey(int S_ID, String title, Timestamp start, Timestamp end, int B_ID, int O_ID, String contents, int voted) {
        this.S_ID = S_ID;
        this.title = title;
        this.start = start;
        this.end = end;
        this.B_ID = B_ID;
        this.O_ID = O_ID;
        this.contents = contents;
        this.voted = voted;
    }
}
