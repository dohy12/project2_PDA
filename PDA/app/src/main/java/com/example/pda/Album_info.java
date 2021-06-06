package com.example.pda;

import java.time.LocalDate;

public class Album_info {
    private int A_ID;
    private String title;
    private String date;
    private String location;
    private int U_ID;
    private String album_intro;

    public Album_info(int a_id, String title, String date, String location, int u_id, String album_intro) {
        this.setA_ID(a_id);
        this.setU_ID(u_id);
        this.setTitle(title);
        this.setDate(date);
        this.setLocation(location);
        this.setAlbum_intro(album_intro);
    }

    public String showJsonString(){
        String res =
                "{\"A_ID\" : "          + getA_ID() +
                ",\"title\" : \""       + getTitle() + "\"" +
                ",\"location\" : \""    + getLocation() + "\"" +
                ",\"date\" : \""        + getDate() + "\"" +
                ",\"U_ID\" : "          + getU_ID() +
                ",\"album_intro\" : \"" + getAlbum_intro() + "\"" +
                "}";

        return res;
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

}