package pda.server.DTO;

import java.sql.Timestamp;

public class Comment {
    public int C_ID;
    public Timestamp dateTime;
    public String contents;
    public int B_ID;
    public int U_ID;
    public int R_CID;

    public Comment(int C_ID, Timestamp dateTime, String contents, int B_ID, int U_ID, int R_CID) {
        this.setC_ID(C_ID);
        this.setB_ID(B_ID);
        this.setDateTime(dateTime);
        this.setContents(contents);
        this.setU_ID(U_ID);
        this.setR_CID(R_CID);
    }

    public int getC_ID() { return C_ID; }
    public void setC_ID(int C_ID) { this.C_ID = C_ID; }

    public int getB_ID() { return B_ID; }
    public void setB_ID(int B_ID) { this.B_ID = B_ID; }

    public Timestamp getDateTime() { return dateTime; }
    public void setDateTime(Timestamp dateTime) { this.dateTime = dateTime; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public int getU_ID() { return U_ID; }
    public void setU_ID(int U_ID) { this.U_ID = U_ID; }

    public int getR_CID() { return R_CID; }
    public void setR_CID(int R_CID) { this.R_CID = R_CID; }
}
