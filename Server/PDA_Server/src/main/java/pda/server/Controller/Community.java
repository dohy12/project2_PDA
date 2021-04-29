package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.Board;

@RestController
public class Community {

    @Autowired
    Board board;
    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.GET)
    public String boardList(@PathVariable("GroupId") String GroupId){

        board.boardList(GroupId);

        return "조회";
    }

    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.POST)
    public String boardPost(@PathVariable("GroupId") String GroupId) {


        return "작성";
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.DELETE)
    public String boardDelete(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID){

        return "삭제";
    }

    @RequestMapping(value = "/Community/{GroupId}/{BID}", method = RequestMethod.PUT)
    public String boardUpdate(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID){

        return "수정";
    }

    /*
    게시글 포맷 정리 먼저
     */

}
