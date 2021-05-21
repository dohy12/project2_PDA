package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.MessageOperation;
import pda.server.DTO.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageCon {

    @Autowired
    MessageOperation msg;

    @GetMapping("/message/send/{GroupId}")
    List<Message> GetSendMessages(@PathVariable("GroupId") String GroupId, @RequestAttribute String U_ID) {
        return msg.getSMessages(GroupId, Integer.parseInt(U_ID));
    }

    @GetMapping("/message/receive/{GroupId}")
    List<Message> GetReceiveMessages(@PathVariable("GroupId") String GroupId, @RequestAttribute String U_ID) {
        return msg.getRMessages(GroupId, Integer.parseInt(U_ID));
    }

    @PostMapping("/message/{GroupId}")
    Map<String, Object> GetMessage(@PathVariable("GroupId") String GroupId, @RequestBody Message body) {
        Map<String, Object> result = new HashMap<>();
        try{
            msg.sendMessages(GroupId, body);
            result.put("result", "메시지 전달 성공");
        }
        catch (Exception e)
        {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 전송 실패");
        }

        return result;
    }

    @DeleteMapping("/message/{GroupId}/{M_ID}")
    Map<String, Object> DelMessage(@PathVariable("M_ID") int M_ID, @PathVariable("GroupId") String GroupId) {
        Map<String, Object> result = new HashMap<>();
        try{
            msg.delectMessage(GroupId, M_ID);
            result.put("result", "성공적으로 삭제");
        }
        catch (Exception e)
        {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 삭제제 실패");
        }

        return result;
    }

}
