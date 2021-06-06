package pda.server.DTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Survey {
    int S_ID;
    String title;
    int B_ID;

    public Survey(int S_ID, String title, int B_ID) {
        this.S_ID = S_ID;
        this.title = title;
        this.B_ID = B_ID;
    }
}
