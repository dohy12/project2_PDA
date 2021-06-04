package com.example.pda;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Board_Info implements Serializable {
    private int boardId;
    private Boolean isNotice;
    private String title;
    private String name;
    private LocalDateTime date;
    private int views_num;
    private int comments_num;
    private String contents;

    public Board_Info(int boardId, Boolean isNotice, String title, String name, LocalDateTime date, int views_num, int comments_num){
        this.boardId = boardId;
        this.isNotice = isNotice;
        this.title =title;
        this.name = name;
        this.date = date;
        this.views_num = views_num;
        this.comments_num = comments_num;
    }

    public Board_Info(int boardId, Boolean isNotice, String title, String name, LocalDateTime date, int views_num, int comments_num, String contents){
        this.boardId = boardId;
        this.isNotice = isNotice;
        this.title =title;
        this.name = name;
        this.date = date;
        this.views_num = views_num;
        this.comments_num = comments_num;
        this.contents = contents;
    }

    public int getBoardId() {
        return boardId;
    }

    public Boolean getNotice() {
        return isNotice;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getViews_num() {
        return views_num;
    }

    public int getComments_num() {
        return comments_num;
    }

    public String getContents() {
        return contents;
    }
}
