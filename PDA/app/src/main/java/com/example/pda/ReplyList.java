package com.example.pda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReplyList {
    private Context mContext;
    private ArrayList<Board_comment> boardCommentList;
    private LinearLayout container;

    public ReplyList(Context mContext, ArrayList<Board_comment> board_commentList, LinearLayout container){
        this.mContext = mContext;
        this.boardCommentList = board_commentList;
        this.container = container;

        showCommentList();
    }

    private void showCommentList(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<boardCommentList.size();i++) {
            Board_comment bc = boardCommentList.get(i);
            View v = inflater.inflate(R.layout.board_comments, null);

            v.findViewById(R.id.profile_image).setClipToOutline(true);

            ((TextView)v.findViewById(R.id.comments_id)).setText(Integer.toString(bc.getCommentID()));
            ((TextView)v.findViewById(R.id.comments_name)).setText(bc.getName());
            ((TextView)v.findViewById(R.id.comments_contents)).setText(bc.getComments());

            String formatDate = bc.getDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            ((TextView)v.findViewById(R.id.comments_date)).setText(formatDate);

            int replyId = bc.getReplyID();
            if(replyId == -1)
                container.addView(v);
            else
            {
                int count = container.getChildCount();

                for(int j=0;j<count;j++)
                {
                    View childV = container.getChildAt(j);

                    int comments_id = Integer.parseInt((String) ((TextView)childV.findViewById(R.id.comments_id)).getText());
                    if(replyId == comments_id)
                        ((LinearLayout)childV.findViewById(R.id.comments_reply_container)).addView(v);
                }
            }
        }
    }

}
