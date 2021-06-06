package pda.server.DTO;

import java.time.LocalDate;

public class Album {
    public int A_ID;
    public String title;
    public String date;
    public String location;
    public int U_ID;
    public String album_intro;    

    public String image_src;
    public int image_cnt;
    public int comment_cnt;

    public Album(int a_id, String title, String date, String location, int u_id, String album_intro, String image_src, int image_cnt, int comment_cnt) {
        this.setA_ID(a_id);
        this.setU_ID(u_id);
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
        this.setAlbum_intro(album_intro);

        this.setImage_src(image_src);
        this.setImage_cnt(image_cnt);
        this.setComment_cnt(comment_cnt);
    }


    public Album(int a_id, String title, String date, String location, int u_id, String album_intro) {
        this.setA_ID(a_id);
        this.setU_ID(u_id);
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
        this.setAlbum_intro(album_intro);
    }

  
    public int getA_ID() {
        return A_ID;
    }

    public int getU_ID() {
        return U_ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getAlbum_intro() {
        return album_intro;
    }

    public String getImage_src() {
        return image_src;
    }

    public int getImage_cnt() {
        return image_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }


    public void setA_ID(int a_ID) {
        A_ID = a_ID;
    }

    public void setU_ID(int u_ID) {
        U_ID = u_ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAlbum_intro(String album_intro) {
        this.album_intro = album_intro;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public void setImage_cnt(int image_cnt) {
        this.image_cnt = image_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

}
