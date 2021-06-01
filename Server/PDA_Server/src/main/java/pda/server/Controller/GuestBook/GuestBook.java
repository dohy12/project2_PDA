package pda.server.Controller.GuestBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pda.server.Controller.RestException;
import pda.server.DAO.MemberOperation;
import pda.server.DTO.Member;
import pda.server.Handler.UserTableMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 *
 */
@RestController
public class GuestBook
{
    @Autowired
    MemberOperation memberOperation;

    @RequestMapping("/GuestBook/{GroupId}/MemberList")
    public List<Member> MemberList(@PathVariable String GroupId)
    {
        Member[] partialInf;
        partialInf = memberOperation.MemberList(GroupId); // 단체 유저 테이블에서 부분 회원 정보  받기
        List<Member> MemberList = new ArrayList<>();
        for (Member partial : partialInf)
        {
            Member oneMember =  memberOperation.details(UserTableMapping.UIDConversion(partial.getUId()),partial.getUId()); //메인 유저 테이블에서 더 상세한 정보 받기
            oneMember.setIntroduction(partial.getIntroduction());
            oneMember.setJointime(oneMember.getJointime());
            oneMember.setUsertablenum(UserTableMapping.UIDConversion(oneMember.getUId()));
            oneMember.setIsadmin(oneMember.getIsadmin());
            MemberList.add(oneMember); //합쳐서 완전한 회원 정보  받기
        }
        return MemberList;
    }

    @RequestMapping("/GuestBook/{GroupId}/setAdmin/{Flag}")
    public String setAdmin(@PathVariable String GroupId ,@PathVariable int Flag, @RequestParam(name = "U_ID" ,required = false) String UID)
    {
//        if (memberOperation.isAdmin(GroupId, Integer.parseInt(UID))!=1)
//        {
//            throw new RestException(HttpStatus.UNAUTHORIZED,"권한 없습니다");
//        }
        memberOperation.setAdmin(GroupId, Flag);
        return "설치 됩니다";

    }

    @RequestMapping("/GuestBook/{GroupId}/waitingToJoin")
    public List<Integer> waitingToJoin(@PathVariable String GroupId , @RequestParam(name = "U_ID" ,required = false) String UID)
    {
//        if (memberOperation.isAdmin(GroupId, Integer.parseInt(UID))!=1)
//        {
//            throw new RestException(HttpStatus.UNAUTHORIZED,"권한 없습니다");
//        }
        return memberOperation.waitingToJoin(GroupId);
    }
}
