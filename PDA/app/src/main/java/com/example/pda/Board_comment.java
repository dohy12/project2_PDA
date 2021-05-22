package com.example.pda;

import java.time.LocalDateTime;

public class Board_comment {
    private int commentID;
    private int replyID;
    private String name;
    private String comments;
    private LocalDateTime date;

    public Board_comment(int commentID, int replyID, String name, String comments, LocalDateTime date){
        this.commentID = commentID;
        this.replyID = replyID;
        this.name = name;
        this.comments = comments;
        this.date = date;
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
}
