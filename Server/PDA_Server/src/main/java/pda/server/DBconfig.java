package pda.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DBconfig {

    @Value("${spring.datasource.password}")
    private String pw;

    @Bean
    public DataSource createRouterDatasource() {
        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        //test용 나중에 그룹, 메인 스키마 추가로 바꿔야함
        String url = "jdbc:mysql://18.206.18.154:3306/DBConnectTest";
        targetDataSources.put("current:test", createDataSource(url, "root", pw));
        url = "jdbc:mysql://18.206.18.154:3306/main";
        targetDataSources.put("current:main", createDataSource(url, "root", pw));
        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }

    private DataSource createDataSource(String url, String user, String password) {
        com.zaxxer.hikari.HikariDataSource dataSource = new com.zaxxer.hikari.HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        return dataSource;
    }
}

