package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Board;
import java.util.List;

@Mapper
public interface BoardOperation {
    //상위 20개 게시글 읽어오기
    //글이 등록된 시간(dateTime) 기준 최근 20개 게시글
    //isNotice = 1 즉 공지글 보기일 경우 전체 공지글 보기, isNotice = 0 즉 전체글 보기일 경우 상단 5개 공지글 이후에 일반 게시글 15개
    @Select("select * from ${GroupId}.board where isNotice = ${isNotice} order by date desc limit 20")
    public List<Board> boardList(@Param("GroupId") String GroupId, @Param("isNotice") int isNotice);

    @Select("(select * from ${GroupId}.board where isNotice = 1 order by date desc limit 5) union (select * from ${GroupId}.board where isNotice = ${isNotice} order by date desc limit 15)")
    public List<Board> boardList2(@Param("GroupId") String GroupId, @Param("isNotice") int isNotice);

    //게시글 검색 기준: 제목/내용에 키워드 포함
    @Select("select * from ${GroupId}.board where title like \"%${Keyword}%\" or contents like \"${Keyword}%\" order by date desc limit 20")
    public List<Board> searchBoard(@Param("GroupId") String GroupId, @Param("Keyword") String Keyword);

    //선택한 하나의 글 정보 읽어오기
    @Select("select * from ${GroupId}.board where B_ID = ${BID}")
    public Board selectedBoard(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //공지사항 작성 시도: 관리자인지 확인
    @Select("select isAdmin from ${GroupId}.user where U_ID = ${UID}")
    public int getIsAdmin(@Param("GroupId") String GroupId, @Param("UID") int UID);

    //게시글 작성하기, 매개변수 타입 수정 필요
    @Insert("insert into ${GroupId}.board values(${Board.B_ID}, ${Board.isNotice}, \"${Board.title}\", \"${Board.dateTime}\", \"${Board.contents}\", ${Board.U_ID})")
    public void boardPost(@Param("GroupId") String GroupId, @Param("Board") Board Board);

    //게시글 삭제하기
    @Delete("delete from ${GroupId}.board where B_ID = ${BID}")
    public void boardDelete(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //게시글 수정하기
    @Update("update ${GroupId}.board set title = \"${Board.title}\", contents = \"${Board.contents}\" where b_id = ${BID}")
    public void boardUpdate(@Param("GroupId") String GroupId, @Param("BID") int BID, @Param("Board") Board Board);
}
