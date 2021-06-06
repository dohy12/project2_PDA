package com.example.pda;


import java.time.LocalDateTime;

public class GuestBook {
    private String name;
    private String comment;
    private LocalDateTime date;
    private String profileimg;

    public GuestBook(String name, String comment, LocalDateTime date){
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public GuestBook(String name, String comment, LocalDateTime date, String profileimg) {
        this.name = name;
        this.comment = comment;
        this.date = date;
        this.profileimg = profileimg;
    }

    public String getName(){
        return name;
    }

    public String getComment(){
        return comment;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public LocalDateTime getDate(){
        return date;
    }
}
