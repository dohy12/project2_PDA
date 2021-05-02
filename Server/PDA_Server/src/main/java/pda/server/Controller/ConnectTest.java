package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.testMapper;


@RestController
public class ConnectTest
{
    @Autowired
    testMapper mapper;

    @RequestMapping("/connectTest")
    public String test(@RequestAttribute String U_ID)
    {
        return U_ID;
    }

    @RequestMapping("/Test/{id}")
    public String CT(@PathVariable String id)
    {
        return String.format("DB: %s", mapper.test(id));
    }
}
