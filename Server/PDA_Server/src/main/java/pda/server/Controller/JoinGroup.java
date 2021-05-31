package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.MemberOperation;
import pda.server.DTO.Member;
import pda.server.Handler.UserTableMapping;

import java.util.*;

@RestController
public class JoinGroup
{
    @Autowired
    MemberOperation memberOperation;

    @RequestMapping("/JoinGroup/{GroupId}")
    public Map<String, Object> Join(@PathVariable String GroupId, @RequestAttribute String U_ID)
    {
        StringBuilder groupsStr = new StringBuilder();
        int num = UserTableMapping.UIDConversion(Integer.parseInt(U_ID));
        String id = memberOperation.FindIDByUid(num, Integer.parseInt(U_ID));
        String awaitingCertification = memberOperation.AwaitingCertification(num, Integer.parseInt(U_ID));
        Map<String, Object> result = new HashMap<>();

        if (awaitingCertification != null)
        {
            List<String> groups = new ArrayList<>(Arrays.asList(awaitingCertification.split(",")));
            groups.add(GroupId);
            for (String s : groups)
            {
                groupsStr.append(s);
                groupsStr.append(",");
            }
            memberOperation.UpdateAwaitingCertification(num, groupsStr.deleteCharAt(groupsStr.length() - 1).toString(), Integer.parseInt(U_ID));
        }
        else
        {
            memberOperation.UpdateAwaitingCertification(num, GroupId, Integer.parseInt(U_ID));
        }
        memberOperation.AddUser(GroupId, Integer.parseInt(U_ID), id);
        result.put("result", "가입 신청 되었습니다");
        return result;
    }

    @RequestMapping("/JoinGroup/Certification/{GroupId}/{MemberUID}")
    public Map<String, Object> Certification(@PathVariable String GroupId, @PathVariable int MemberUID, @RequestAttribute String U_ID)
    {
//        if (memberOperation.isAdmin(GroupId, U_ID) != 1)
//        {
//            throw new RestException(HttpStatus.UNAUTHORIZED, "권한 없습니다");
//        }
        int num = UserTableMapping.UIDConversion(MemberUID);
        StringBuilder groupsStr = new StringBuilder();
        String awaitingCertification = memberOperation.AwaitingCertification(num, MemberUID);
        String joinedGroups = memberOperation.JoinedGroups(num, MemberUID);
        List<String> groups = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        if (joinedGroups != null)
        {
            groups.addAll(Arrays.asList(joinedGroups.split(",")));
            groups.add(GroupId);
            for (String s : groups)
            {
                groupsStr.append(s);
                groupsStr.append(",");
            }
            memberOperation.UpdateJoinedGroups(num, groupsStr.deleteCharAt(groupsStr.length() - 1).toString(), MemberUID);
        }
        else
        {
            memberOperation.UpdateJoinedGroups(num, GroupId, MemberUID);
        }

        groups.clear();
        groups.addAll(Arrays.asList(awaitingCertification.split(",")));
        groups.remove(GroupId);
        groupsStr = new StringBuilder();
        for (String s : groups)
        {
            groupsStr.append(s);
            groupsStr.append(",");
        }
        if (groups.isEmpty()) memberOperation.UpdateAwaitingCertificationToNull(num, MemberUID);
        else
            memberOperation.UpdateAwaitingCertification(num, groupsStr.deleteCharAt(groupsStr.length() - 1).toString(), MemberUID);
        memberOperation.Certification(GroupId, MemberUID);
        result.put("result", "승인 되었습니다");
        return result;
    }

    @RequestMapping("/JoinGroup/Certification/List/{GroupId}")
    public List<Member> List(@PathVariable String GroupId, @RequestAttribute String U_ID)
    {
//        if (memberOperation.isAdmin(GroupId, U_ID) != 1)
//        {
//            throw new RestException(HttpStatus.UNAUTHORIZED, "권한 없습니다");
//        }
        return memberOperation.WaitingList(GroupId);
    }
}
