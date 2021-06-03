package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.BoardOperation;
import pda.server.DTO.Board;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class Community {

    @Autowired
    BoardOperation board;
    Timestamp dateTime = new Timestamp(System.currentTimeMillis());
    Board Board = new Board(0,(short)1,"BoardClassTitle",dateTime,"BoardClassContents",1503238549);

    //게시글 목록을 보여줄 때 사용
    //상위 20개 게시글 정보를 불러옴
    //URL의 isNotice로 공지사항과 일반게시글 구분
    @RequestMapping(value = "/Community/{GroupId}/{isNotice}", method = RequestMethod.GET)
    public List<Board> boardList(@PathVariable("GroupId") String GroupId, @PathVariable("isNotice") int isNotice) {

        List<Board> showBoards = null;

        try{
            showBoards = board.boardList(GroupId, isNotice);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return showBoards;
    }

    //하나의 게시글 정보 조회할 때 사용
    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.GET)
    public Board selectedBoard(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID) {

        return board.selectedBoard(GroupId, BID);
    }

    //게시글 검색 기능
    //키워드를 제목/내용에 포함하는 글
    @RequestMapping(value = "/Community/{GroupId}/{Keyword}", method = RequestMethod.GET)
    public List<Board> searchBoard(@PathVariable("GroupId") String GroupId, @PathVariable("Keyword") String Keyword) {

        return board.searchBoard(GroupId, Keyword);
    }

    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.POST)
    public String boardPost(@PathVariable("GroupId") String GroupId) {

        int authUID = board.getIsAdmin(GroupId, Board.getU_ID());

        try{
            if(Board.getIsNotice() == 1 && authUID == 1) {
                board.boardPost(GroupId, Board);
            } else if(Board.getIsNotice() == 0) {
                board.boardPost(GroupId, Board);
            } else {
                return "작성 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"boardPost()\" Failed";
        }

        return "작성";
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.DELETE)
    public String boardDelete(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID, @RequestAttribute String U_ID){

        Board temp = board.selectedBoard(GroupId, BID);
        int authUID = Integer.parseInt(U_ID);

        try {
            if (temp.getU_ID() == authUID) {
                board.boardDelete(GroupId, BID);
            } else {
                return "삭제 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"boardDelete()\" Failed";
        }

        return "삭제";
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.PUT)
    public String boardUpdate(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID, @RequestAttribute String U_ID){

        Board temp = board.selectedBoard(GroupId, BID);
        int authUID = Integer.parseInt(U_ID);

        try{
            if(temp.getU_ID() == authUID) {
                board.boardUpdate(GroupId, BID, Board);
            } else {
                return "수정 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"boardUpdate()\" Failed.";
        }

        return "수정";
    }

    //삭제, 수정: 해당 작업을 시도하는 이용자의 UID와 게시글의 UID가 같은지 확인
    //공지사항의 작성 작업을 시도하는 이용자의 UID가 관리자로 등록되어 있는지 확인
    //공지사항의 삭제, 수정 작업을 시도하는 일반 이용자에 대해서는 UID 일치 비교만으로도 거를 수 있음
}