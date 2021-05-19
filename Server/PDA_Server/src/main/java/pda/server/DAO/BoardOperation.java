package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Board;
import java.util.List;

@Mapper
public interface BoardOperation {
    @Select("select * from ${GroupId}.board limit 20")
    public List<Board> boardList(@Param("GroupId") String GroupId);
    //상위 20개 게시글 읽어오기

    @Insert("insert into ${GroupId}.board(B_ID, isNotice, title, date, contents, U_ID) values (${Board.B_ID}, ${Board.isNotice}, #{Board.title}, sysdate, #{Board.contents}, ${Board.U_ID})")
    public void boardPost(@Param("GroupId") String GroupId, @Param("Board") Board Board);
    //게시글 작성하기, 매개변수 타입 수정 필요

    @Delete("delete from ${GroupId}.board where B_ID = ${BID}")
    public void boardDelete(@Param("GroupId") String GroupId, @Param("BID") int BID);
    //게시글 삭제하기

    //@Update
    //추가 예정
}
