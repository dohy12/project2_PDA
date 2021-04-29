package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pda.server.DAO.testMapper;


@RestController
public class ConnectTest
{
    @Autowired
    testMapper mapper;

    @RequestMapping("/connectTest")
    public String test()
    {
        return "접속 성공되었다";
    }

    @RequestMapping("/Test/{id}")
    public String CT(@PathVariable String id)
    {
        return String.format("DB: %s", mapper.test(id));
    }
}
