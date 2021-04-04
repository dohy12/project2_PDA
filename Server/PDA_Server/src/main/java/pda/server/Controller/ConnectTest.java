package pda.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectTest
{
    @RequestMapping("connectTest")
    public String test()
    {
        return "접속 성공되었다";
    }
}
