package pda.server.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextListener;
import pda.server.RoutingDatabaseContextHolder;
import pda.server.myMapper;

import javax.servlet.http.HttpSession;

@RestController
public class ConnectTest
{
    private final myMapper mapper;

    public ConnectTest(myMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping("/connectTest")
    public String test()
    {
        return "접속 성공되었다";
    }

    @RequestMapping("/Test/{id}")
    public String CT(@PathVariable String id)
    {
        RoutingDatabaseContextHolder.set(id);
        return String.format("DB: %s", mapper.test());
    }
}
