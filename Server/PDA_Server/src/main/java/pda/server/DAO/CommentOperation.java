package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Comment;
import java.util.List;

@Mapper
public interface CommentOperation {
    //해당 글에 속한 댓글들 읽어오기
    //오래된 댓글부터 순서대로
    @Select("select * from ${GroupId}.board_comment where B_ID = ${BID} order by date")
    public List<Comment> commentList(@Param("GroupId") String GroupId, @Param("BID") String BID);
    public List<Comment> commentList(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //하나의 댓글 선택하기
    @Select("select * from ${GroupId}.board_comment where C_ID = ${CID}")
    public Comment selectedComment(@Param("GroupId") String GroupId, @Param("CID") int CID);

    //댓글 입력하기
    @Insert("insert into ${GroupId}.board_comment values(${Comment.C_ID}, ${Comment.dateTime}, \"${Comment.contents}\", ${Comment.B_ID}, ${Comment.U_ID}, ${Comment.R_CID})")
    public void commentPost(@Param("GroupId") String GroupId, @Param("Comment") Comment Comment);

    //댓글 삭제하기
    @Delete("delete from ${GroupId}.board_comment where C_ID = ${CID}")
    public void commentDelete(@Param("GroupId") String GroupId, @Param("CID") int CID);

    //댓글 수정하기
    @Update("update ${GroupId}.board_comment set contents = \"${Comment.contents}\" where C_ID = ${CID}")
    public void commentUpdate(@Param("GroupId") String GroupId, @Param("Comment") Comment Comment, @Param("CID") int CID);
}