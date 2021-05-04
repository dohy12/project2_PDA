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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * 아직 테스트하지 않았습니다
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
            Member oneMember = (Member) memberOperation.details(partial.getUsertablenum(),partial.getUId()); //메인 유저 테이블에서 더 상세한 정보 받기
            oneMember.setIntroduction(partial.getIntroduction());
            oneMember.setJointime(oneMember.getJointime());
            oneMember.setUsertablenum(oneMember.getUsertablenum());
            oneMember.setIsadmin(oneMember.getIsadmin());
            MemberList.add(oneMember); //합쳐서 완전한 회원 정보  받기
        }
        return MemberList;
    }

    @RequestMapping("/GuestBook/{GroupId}/setAdmin/{Flag}")
    public String setAdmin(@PathVariable String GroupId ,@PathVariable int Flag, @RequestParam(name = "UID" ,required = false) int UID)
    {
        if (memberOperation.isAdmin(GroupId, UID)!=1)
        {
            throw new RestException(HttpStatus.UNAUTHORIZED,"권한 없습니다");
        }
        memberOperation.setAdmin(GroupId, Flag);
        return "설치 됩니다";

    }

    @RequestMapping("/GuestBook/{GroupId}/waitingToJoin")
    public Map<String,Object> waitingToJoin(@PathVariable String GroupId , @RequestParam(name = "UID" ,required = false) int UID)
    {
        if (memberOperation.isAdmin(GroupId, UID)!=1)
        {
            throw new RestException(HttpStatus.UNAUTHORIZED,"권한 없습니다");
        }
        return memberOperation.waitingToJoin(GroupId);
    }
}
