package com.example.pda;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Board_comment implements Serializable {
    private int commentID;
    private int replyID;
    private String name;
    private String comments;
    private LocalDateTime date;
    private int UID;

    public Board_comment(int commentID, int replyID, String name, String comments, LocalDateTime date, int uid){
        this.commentID = commentID;
        this.replyID = replyID;
        this.name = name;
        this.comments = comments;
        this.date = date;
        this.UID = uid;
    }

    public int getCommentID() {
        return commentID;
    }

    public int getReplyID() {
        return replyID;
    }

    public String getName() {
        return name;
    }

    public String getComments() {
        return comments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getUID() {
        return UID;
    }
}
