package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.CommentOperation;
import pda.server.DTO.Comment;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
public class BoardComment {

    @Autowired
    CommentOperation comment;

    @RequestMapping(value = "/BoardComment/{GroupId}/{BID}", method = RequestMethod.GET)
    public List<Comment> commentList(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID) {

        List<Comment> showComments = null;

        try {
            showComments = comment.commentList(GroupId, BID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return showComments;
    }

    @RequestMapping(value = "/BoardComment/{GroupId}", method = RequestMethod.POST)
    public String commentPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {
        int cid = (int)params.get("CID");
        String contents = (String)params.get("contents");
        int bid = (int)params.get("BID");
        int uid = (int)params.get("UID");
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        Comment Comment = new Comment(cid, dateTime, contents, bid, uid, 0);

        try {
            comment.commentPost(GroupId, Comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "작성";
    }

    @RequestMapping(value = "/BoardComment/{GroupId}/{CID}", method = RequestMethod.DELETE)
    public String commentDelete(@PathVariable("GroupId") String GroupId, @PathVariable("CID") int CID, @RequestAttribute String U_ID) {
        Comment temp = comment.selectedComment(GroupId, CID);
        int authUID = Integer.parseInt(U_ID);

        try {
            if(temp.getU_ID() == authUID) {
                comment.commentDelete(GroupId, CID);
            } else {
                return "삭제 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"commentDelete()\" Failed";
        }

        return "삭제";
    }

    @RequestMapping(value = "/BoardComment/{GroupId}/{CID}", method = RequestMethod.PUT)
    public String commentUpdate(@PathVariable("GroupId") String GroupId, @PathVariable("CID") int CID, @RequestAttribute String U_ID){
        Comment temp = comment.selectedComment(GroupId, CID);
        int authUID = Integer.parseInt(U_ID);

        try{
            if(temp.getU_ID() == authUID){
                temp.setContents("mod");
                comment.commentUpdate(GroupId, temp, CID);
            } else {
                return "수정 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"commentUpdate()\" Failed";
        }

        return "수정";
    }
}