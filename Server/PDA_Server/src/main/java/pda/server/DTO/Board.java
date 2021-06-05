package pda.server.DTO;

import java.sql.Timestamp;

public class Board {
    public int B_ID;
    public short isNotice;
    public String title;
    public Timestamp dateTime;
    public String contents;
    public int U_ID;
    public int views_num;
    public String name;

    public Board(int B_ID, short isNotice, String title, Timestamp dateTime, String contents, int U_ID, int views_num, String name) {
        this.setB_ID(B_ID);
        this.setIsNotice(isNotice);
        this.setTitle(title);
        this.setDateTime(dateTime);
        this.setContents(contents);
        this.setU_ID(U_ID);
        this.setViews_num(views_num);
        this.setName(name);
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Timestamp getDateTime(){
        return dateTime;
    }
    public void setDateTime(Timestamp dateTime){
        this.dateTime = dateTime;
    }

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

    public int getViews_num() { return views_num; }
    public void setViews_num(int views_num) { this.views_num = views_num; }

}
