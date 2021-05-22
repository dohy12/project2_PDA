package com.example.pda;


import java.time.LocalDateTime;

public class GuestBook {
    private String name;
    private String comment;
    private LocalDateTime date;

    public GuestBook(String name, String comment, LocalDateTime date){
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public String getComment(){
        return comment;
    }

    public LocalDateTime getDate(){
        return date;
    }
}
