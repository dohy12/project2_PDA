package pda.server.Controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pda.server.DAO.BoardOperation;
import pda.server.DAO.ImageOperation;
import pda.server.DTO.Board;
import pda.server.Handler.UserTableMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Community {

    @Autowired
    BoardOperation board;
    @Autowired
    ImageOperation imageOperation;
    Timestamp dateTime;
    //게시글 목록을 보여줄 때 사용
    //상위 20개 게시글 정보를 불러옴
    //URL의 isNotice로 공지사항과 일반게시글 구분

    @RequestMapping(value = "/Community/{GroupId}/{isNotice}", method = RequestMethod.GET)
    public List<Board> boardList(@PathVariable("GroupId") String GroupId, @PathVariable("isNotice") int isNotice) {

        List<Board> showBoards = null;
        String nameTemp;
        int uidTemp;

        try{
            if(isNotice == 1)
                showBoards = board.boardList(GroupId, isNotice, 20);
            else {
                showBoards = board.boardList(GroupId, 1, 5);
                showBoards.addAll(board.boardList2(GroupId, isNotice));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return showBoards;
    }

    //하나의 게시글 정보 조회할 때 사용
    @RequestMapping(value = "/Community/{GroupId}/select/{BID}", method = RequestMethod.GET)
    public Board selectedBoard(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID) {

        return board.selectedBoard(GroupId, BID);
    }

    //게시글 검색 기능
    //키워드를 제목/내용에 포함하는 글
    @RequestMapping(value = "/Community/{GroupId}/search/{Keyword}", method = RequestMethod.GET)
    public List<Board> searchBoard(@PathVariable("GroupId") String GroupId, @PathVariable("Keyword") String Keyword) {

        return board.searchBoard(GroupId, Keyword);
    }

    @RequestMapping(value = "/Community/{GroupId}/next", method = RequestMethod.GET)
    public int nextBID(@PathVariable("GroupId") String GroupId){
        int bid = board.nextBID(GroupId);

        return bid;
    }

    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.POST)
    public String boardPost(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {

        int bid = (int)params.get("B_ID");
        short isNotice;
        if((int)params.get("isNotice") == 1)
            isNotice = 1;
        else
            isNotice = 0;
        String title = (String)params.get("title");
        dateTime = new Timestamp(System.currentTimeMillis());
        String contents = (String)params.get("contents");
        int uid = (int)params.get("U_ID");

        int authUID = board.getIsAdmin(GroupId, uid);

        Board Board = new Board(bid, isNotice, title, dateTime, contents, uid, 0, "name", 0);

        try{
            if(Board.getIsNotice() == 1 && authUID == 1) {
                dateTime = new Timestamp(System.currentTimeMillis());
                board.boardPost(GroupId, Board);
            } else if(Board.getIsNotice() == 0) {
                board.boardPost(GroupId, Board);
            } else {
                return "작성 권한이 없습니다.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: \"boardPost()\" Failed";
        }

        return "작성";
    }


    @PostMapping("/Community/image/{src}")
    public Map<String, Object> saveImage(@PathVariable String src, @RequestParam("image") MultipartFile file)
    {
        String newSrc = src.replaceAll("'", "");

        Map<String, Object> m = new HashMap<>();
        try{
            InputStream in = file.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, "png", new File("/home/ubuntu/Server/images/"+newSrc));
            throw new RestException(HttpStatus.OK, "이미지 저장 성공");
        } catch (IOException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 실패");
        } catch (RestException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.DELETE)
    public String boardDelete(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID, @RequestAttribute String U_ID){

        Board temp = board.selectedBoard(GroupId, BID);
        ArrayList<File> file = new ArrayList<File>();

        int img = 0;

        int authUID = Integer.parseInt(U_ID);
        img = imageOperation.imageExist(GroupId, BID);

        for(int i=0; i < img; i++) {
            System.out.println("boardImg_" + BID + "_" + i + ".png is preparing for deletion");
            file.add(new File("/home/ubuntu/Server/images/" + "boardImg_" + BID + "_" + i + ".png"));
        }

        try {
            if (temp.getU_ID() == authUID) {
                board.boardDelete(GroupId, BID);
                for(int i=0; i < img; i++) {
                    if(file.get(i).delete()) System.out.println("delete");
                    else System.out.println(file.get(i).exists());
                }
            } else {
                return "삭제 권한이 없습니다.";
            }
        } catch (Exception e) {
            return "Error: \"boardDelete()\" Failed\n" + e.getMessage();
        }

        return "삭제";
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.PUT)
    public String boardUpdate(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID, @RequestBody Map<String, Object> params, @RequestAttribute String U_ID){

        Board temp = board.selectedBoard(GroupId, BID);

        int bid = (int)params.get("B_ID");
        short isNotice;
        if((int)params.get("isNotice") == 1)
            isNotice = 1;
        else
            isNotice = 0;
        String title = (String)params.get("title");
        String contents = (String)params.get("contents");
        int uid = (int)params.get("U_ID");
        int views_num = (int)params.get("views_num");

        Board Board = new Board(bid, isNotice, title, temp.getDateTime(), contents, uid, views_num, "name", 0);

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

    @RequestMapping(value = "/Community/{GroupId}/views/{BID}", method = RequestMethod.PUT)
    public void viewsNumOper(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID) {
        board.viewsNumOper(GroupId, BID);
    }

    //삭제, 수정: 해당 작업을 시도하는 이용자의 UID와 게시글의 UID가 같은지 확인
    //공지사항의 작성 작업을 시도하는 이용자의 UID가 관리자로 등록되어 있는지 확인
    //공지사항의 삭제, 수정 작업을 시도하는 일반 이용자에 대해서는 UID 일치 비교만으로도 거를 수 있음
}