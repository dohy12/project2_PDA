package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.BoardOperation;
import pda.server.DTO.Board;

import java.util.List;

@RestController
public class Community {

    @Autowired
    BoardOperation board;
    Board Board = new Board(0, (short) 0, "TiTle(test)", "Contents(test)", 1);
    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.GET)
    public List<Board> boardList(@PathVariable("GroupId") String GroupId){

        return board.boardList(GroupId);

    }

    @RequestMapping(value = "/Community/{GroupId}", method = RequestMethod.POST)
    public String boardPost(@PathVariable("GroupId") String GroupId, @PathVariable("Board") Board Board) {

        board.boardPost(GroupId, Board);

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

}
