package pda.server.DAO;

import org.apache.ibatis.annotations.*;

@Mapper
public interface Board {
    @Select("use ${GroupId};" + "select * from board limit 20")
    public void boardList(@Param("GroupId") String GroupId);
    //상위 20개 게시글 읽어오기

    @Insert("insert into ${GroupId}.board values ()")
    public void boardPost(@Param("GroupId") String GroupId);
    //게시글 작성하기, 매개변수 타입 수정 필요

    @Delete("use ${GroupId};" + "delete from board where B_ID = ${BID}")
    public void boardDelete(@Param("GroupId") String GroupId, @Param("BID") int BID);
    //게시글 삭제하기

    //@Update
    //추가 예정
}
