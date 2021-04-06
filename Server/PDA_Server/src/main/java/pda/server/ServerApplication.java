package pda.server;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;


@EnableEncryptableProperties
@SpringBootApplication
public class ServerApplication
{

    public static void main(String[] args)
    {
        RoutingDatabaseContextHolder.set("main");
        SpringApplication.run(ServerApplication.class, args);
    }
}
