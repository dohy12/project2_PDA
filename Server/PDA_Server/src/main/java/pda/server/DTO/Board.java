package pda.server.DTO;

/*import java.util.Date;
import java.text.SimpleDateFormat;*/

public class Board {
    public int B_ID;
    public short isNotice;
    public String title;
//    public Date dateTime;
    public String contents;
    public int U_ID;

    public Board(int B_ID, short isNotice, String title, String contents, int U_ID){
        this.setB_ID(B_ID);
        this.setIsNotice(isNotice);
        this.setTitle(title);
        this.setContents(contents);
        this.setU_ID(U_ID);
    }

    public int getB_ID(){
        return B_ID;
    }
    public void setB_ID(int B_ID){
        this.B_ID = B_ID;
    }

    public short getIsNotice(){
        return isNotice;
    }
    public void setIsNotice(short isNotice){
        this.isNotice = isNotice;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    /*public Date getDateTime(){
        return dateTime;
    }
    public void setDateTime(Date dateTime){
        this.dateTime = dateTime;
    }*/

    public String getContents(){
        return contents;
    }
    public void setContents(String contents){
        this.contents = contents;
    }

    public int getU_ID(){
        return U_ID;
    }
    public void setU_ID(int U_ID){
        this.U_ID = U_ID;
    }
}
