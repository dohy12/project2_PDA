package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.GetGroups;
import pda.server.DTO.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 그룹들을 불러오는 역활
 */

@RestController
public class GetGroupInfo {
    @Autowired
    GetGroups groups;

    //모든 그룹 가져오기
    @GetMapping("/groups")
    public List<group> GetAGroups()
    {
        return groups.getAllGroups();
    }

    //나의 그룹 가져오기
    @GetMapping("/group/Joined/{ID}")
    public List<group> GetJoinedMyGroups(@PathVariable String ID, @RequestAttribute String U_ID)
    {
        List<group> result = new ArrayList<>();
        String temp = groups.getJoinedGroups(Math.abs(ID.hashCode()), Integer.parseInt(U_ID));
        List<String> gids = new ArrayList<>(Arrays.asList(temp.split(",")));
        for(String gid: gids)
        {
            group g = new group();
            g.setGroupId(gid);
            g.setName(groups.getGroupName(g.getGroupId()));
            result.add(g);
        }
        return result;
    }

    @GetMapping("/group/Joined/{ID}")
    public List<group> GetAwaitingMyGroups(@PathVariable String ID, @RequestAttribute String U_ID)
    {
        List<group> result = new ArrayList<>();
        String temp = groups.getAwaitingGroups(Math.abs(ID.hashCode()), Integer.parseInt(U_ID));;
        List<String> gids = new ArrayList<>(Arrays.asList(temp.split(",")));
        for(String gid: gids)
        {
            group g = new group();
            g.setGroupId(gid);
            g.setName(groups.getGroupName(g.getGroupId()));
            result.add(g);
        }
        return result;
    }
}
