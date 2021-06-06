package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Board;
import java.util.List;

@Mapper
public interface BoardOperation {
    //상위 20개 게시글 읽어오기
    //글이 등록된 시간(dateTime) 기준 최근 20개 게시글
    //isNotice = 1 즉 공지글 보기일 경우 전체 공지글 보기, isNotice = 0 즉 전체글 보기일 경우 상단 5개 공지글 이후에 일반 게시글 15개
    @Select("select B.* , userTable.name, ifnull(C.comments_num, 0) as comments_num" +
            " from ${GroupId}.board B" +
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
            " On B.U_ID = userTable.U_ID" +
            " LEFT Join" +
            " (select b_id, count(*) as comments_num" +
            " from ${GroupId}.board_comment group by b_id) C" +
            " On B.b_id = C.b_id" +
            " where isNotice = 1" +
            " order by date desc limit ${limit}")
    public List<Board> boardList(@Param("GroupId") String GroupId, @Param("isNotice") int isNotice, @Param("limit") int limit);

    @Select("select B.* , userTable.name, ifnull(C.comments_num, 0) as comments_num" +
            " from ${GroupId}.board B" +
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
            " On B.U_ID = userTable.U_ID" +
            " LEFT Join" +
            " (select b_id, count(*) as comments_num" +
            " from ${GroupId}.board_comment group by b_id) C" +
            " On B.b_id = C.b_id" +
            " where isNotice = 0" +
            " order by date desc limit 15")
    public List<Board> boardList2(@Param("GroupId") String GroupId, @Param("isNotice") int isNotice);

    //게시글 검색 기준: 제목/내용에 키워드 포함
    @Select("select * from ${GroupId}.board where title like \"%${Keyword}%\" or contents like \"${Keyword}%\" order by date desc limit 20")
    public List<Board> searchBoard(@Param("GroupId") String GroupId, @Param("Keyword") String Keyword);

    //선택한 하나의 글 정보 읽어오기
    @Select("select B.* , userTable.name, ifnull(C.comments_num, 0) as comments_num" +
            " from ${GroupId}.board B" +
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
            " On B.U_ID = userTable.U_ID" +
            " LEFT Join" +
            " (select b_id, count(*) as comments_num" +
            " from ${GroupId}.board_comment group by b_id) C" +
            " On B.b_id = C.b_id" +
            " where B.B_ID = ${BID}")
    public Board selectedBoard(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //공지사항 작성 시도: 관리자인지 확인
    @Select("select ifnull(isAdmin, 0) as res from ${GroupId}.user where U_ID = ${UID}")
    public int getIsAdmin(@Param("GroupId") String GroupId, @Param("UID") int UID);

    //uid로 name 읽어오기
    @Select("select name from main.user${Num} where U_ID = ${UID}")
    public String getName(@Param("Num") int Num, @Param("UID") int UID);


    //게시글 작성하기, 매개변수 타입 수정 필요
    @Insert("insert into ${GroupId}.board values(${Board.B_ID}, ${Board.isNotice}, \"${Board.title}\", \"${Board.dateTime}\", \"${Board.contents}\", ${Board.U_ID}, ${Board.views_num})")
    public void boardPost(@Param("GroupId") String GroupId, @Param("Board") Board Board);

    //게시글 삭제하기
    @Delete("delete from ${GroupId}.board where B_ID = ${BID}")
    public void boardDelete(@Param("GroupId") String GroupId, @Param("BID") int BID);

    //게시글 수정하기
    @Update("update ${GroupId}.board set title = \"${Board.title}\", contents = \"${Board.contents}\" where b_id = ${BID}")
    public void boardUpdate(@Param("GroupId") String GroupId, @Param("BID") int BID, @Param("Board") Board Board);

    //조회수 처리
    @Update("update ${GroupId}.board set views_num = views_num + 1 where b_id = ${BID}")
    public void viewsNumOper(@Param("GroupId") String GroupId, @Param("BID") int BID);
}
