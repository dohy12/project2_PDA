package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.MemberOperation;
import pda.server.DTO.Member;
import pda.server.Handler.UserTableMapping;

import java.rmi.server.UID;
@RestController
public class UserInf
{

    @Autowired
    MemberOperation memberOperation;

    @RequestMapping("/UserInf")
    public Member GetUserInf(@RequestAttribute String U_ID)
    {
        System.out.println("U_ID = " + U_ID);
        return memberOperation.details(UserTableMapping.UIDConversion(Integer.parseInt(U_ID)), Integer.parseInt(U_ID));
    }

}
