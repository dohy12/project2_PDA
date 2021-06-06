package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Comment;
import java.util.List;

@Mapper
public interface CommentOperation {
    //해당 글에 속한 댓글들 읽어오기
    //오래된 댓글부터 순서대로
    @Select("select C.C_ID, C.date, C.contents, C.B_ID, C.U_ID, ifNull(C.R_CID, -1) , userTable.name" +
            " from ${GroupId}.board_comment C" +
            " LEFT Join" +
            " (select U_ID," +
            " case" +
            " when(U_ID between 0 and 214748363) THEN (select name from main.user0 where ${GroupId}.user.U_ID = main.user0.U_ID)" +
            " when(U_ID between 214748364 and 429496727) THEN (select name from main.user1 where ${GroupId}.user.U_ID = main.user1.U_ID)" +
            " when(U_ID between 429496728 and 644245091) THEN (select name from main.user2 where ${GroupId}.user.U_ID = main.user2.U_ID)" +
            " when(U_ID between 644245092 and 858993455) THEN (select name from main.user3 where ${GroupId}.user.U_ID = main.user3.U_ID)" +
            " when(U_ID between 858993456 and 1073741819) THEN (select name from main.user4 where ${GroupId}.user.U_ID = main.user4.U_ID)" +
            " when(U_ID between 1073741820 and 1288490183) THEN (select name from main.user5 where ${GroupId}.user.U_ID = main.user5.U_ID)" +
            " when(U_ID between 1288490184 and 1503238547) THEN (select name from main.user6 where ${GroupId}.user.U_ID = main.user6.U_ID)" +
            " when(U_ID between 1503238548 and 1717986911) THEN (select name from main.user7 where ${GroupId}.user.U_ID = main.user7.U_ID)" +
            " when(U_ID between 1717986912 and 1932735275) THEN (select name from main.user8 where ${GroupId}.user.U_ID = main.user8.U_ID)" +
            " Else (select name from main.user9 where ${GroupId}.user.U_ID = main.user9.U_ID)" +
            " end as name" +
            " from ${GroupId}.user) userTable" +
            " On C.U_ID = userTable.U_ID" +
            " where B_ID = ${BID}" +
            " order by date")
    public List<Comment> commentList(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //하나의 댓글 선택하기
    @Select("select C.C_ID, C.date, C.contents, C.B_ID, C.U_ID, ifNull(C.R_CID, -1) , userTable.name" +
            " from ${GroupId}.board_comment C" +
            " LEFT Join" +
            " (select U_ID," +
            " case" +
            " when(U_ID between 0 and 214748363) THEN (select name from main.user0 where ${GroupId}.user.U_ID = main.user0.U_ID)" +
            " when(U_ID between 214748364 and 429496727) THEN (select name from main.user1 where ${GroupId}.user.U_ID = main.user1.U_ID)" +
            " when(U_ID between 429496728 and 644245091) THEN (select name from main.user2 where ${GroupId}.user.U_ID = main.user2.U_ID)" +
            " when(U_ID between 644245092 and 858993455) THEN (select name from main.user3 where ${GroupId}.user.U_ID = main.user3.U_ID)" +
            " when(U_ID between 858993456 and 1073741819) THEN (select name from main.user4 where ${GroupId}.user.U_ID = main.user4.U_ID)" +
            " when(U_ID between 1073741820 and 1288490183) THEN (select name from main.user5 where ${GroupId}.user.U_ID = main.user5.U_ID)" +
            " when(U_ID between 1288490184 and 1503238547) THEN (select name from main.user6 where ${GroupId}.user.U_ID = main.user6.U_ID)" +
            " when(U_ID between 1503238548 and 1717986911) THEN (select name from main.user7 where ${GroupId}.user.U_ID = main.user7.U_ID)" +
            " when(U_ID between 1717986912 and 1932735275) THEN (select name from main.user8 where ${GroupId}.user.U_ID = main.user8.U_ID)" +
            " Else (select name from main.user9 where ${GroupId}.user.U_ID = main.user9.U_ID)" +
            " end as name" +
            " from ${GroupId}.user) userTable" +
            " On C.U_ID = userTable.U_ID" +
            " where C_ID = ${CID}")
    public Comment selectedComment(@Param("GroupId") String GroupId, @Param("CID") int CID);

    //댓글 입력하기
    @Insert("insert into ${GroupId}.board_comment values(${Comment.C_ID}, \"${Comment.dateTime}\", \"${Comment.contents}\", ${Comment.B_ID}, ${Comment.U_ID}, null)")
    public void commentPost(@Param("GroupId") String GroupId, @Param("Comment") Comment Comment);

    //댓글 삭제하기
    @Delete("delete from ${GroupId}.board_comment where C_ID = ${CID}")
    public void commentDelete(@Param("GroupId") String GroupId, @Param("CID") int CID);

    //댓글 수정하기
    @Update("update ${GroupId}.board_comment set contents = \"${Comment.contents}\" where C_ID = ${CID}")
    public void commentUpdate(@Param("GroupId") String GroupId, @Param("Comment") Comment Comment, @Param("CID") int CID);
}