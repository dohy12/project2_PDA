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

    @RequestMapping(value = "/BoardComment/{UID}", method = RequestMethod.GET)
    public String getProfileImage(@PathVariable("UID") int UID) {
        int Num = UID / (2147483647 / 10);
        String url = comment.getProfileImage(Num, UID);

        return url;
    }

    @RequestMapping(value = "/BoardComment/{GroupId}", method = RequestMethod.POST)
    public String commentPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {
        int cid = (int)params.get("CID");
        String contents = (String)params.get("contents");
        int bid = (int)params.get("BID");
        int uid = (int)params.get("UID");
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());

        Comment Comment = new Comment(cid, dateTime, contents, bid, uid, 0, "name");

        try {
            comment.commentPost(GroupId, Comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "작성";
    }

    @RequestMapping(value = "/BoardComment/{GroupId}/{CID}/{UID}", method = RequestMethod.DELETE)
    public String commentDelete(@PathVariable("GroupId") String GroupId, @PathVariable("CID") int CID, @PathVariable("UID") int UID) {
        Comment Comment = comment.selectedComment(GroupId, CID);

        if (UID == Comment.getU_ID())
            comment.commentDelete(GroupId, CID);
        else
            return "삭제 권한이 없습니다.";

        return "삭제";
    }

    @RequestMapping(value = "/BoardComment/{GroupId}/{CID}/{UID}", method = RequestMethod.PUT)
    public String commentUpdate(@PathVariable("GroupId") String GroupId, @PathVariable("CID") int CID, @PathVariable("UID") int UID, @RequestBody Map<String, Object> params){

        Comment temp = comment.selectedComment(GroupId, CID);

        int cid = (int)params.get("C_ID");
        String contents = (String)params.get("contents");

        if(UID == temp.getU_ID()){
            Comment Comment = new Comment(cid, temp.getDateTime(), contents, temp.getB_ID(), temp.getU_ID(), temp.getR_CID(), temp.getName());
            try{
                comment.commentUpdate(GroupId, Comment, CID);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            return "수정 권한이 없습니다.";
        }
        return "수정";
    }
}