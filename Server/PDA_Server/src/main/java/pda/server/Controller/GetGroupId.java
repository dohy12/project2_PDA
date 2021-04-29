package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.GetGroups;
import pda.server.DTO.group;

import java.util.List;

/**
 * 그룹들을 불러오는 역활
 */

@RestController
public class GetGroupId {
    @Autowired
    GetGroups groups;

    @GetMapping("/groups")
    public List<group> GetGroups()
    {
        return groups.get();
    }
}
