package pda.server;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;



@EnableEncryptableProperties
@ServletComponentScan
@SpringBootApplication
public class ServerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ServerApplication.class, args);
    }


}
