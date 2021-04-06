package pda.server.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pda.server.RoutingDataSourceContextHolder;
import pda.server.myMapper;


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
        RoutingDataSourceContextHolder.set(id);
        return String.format("DB: %s", mapper.test());
    }
}
