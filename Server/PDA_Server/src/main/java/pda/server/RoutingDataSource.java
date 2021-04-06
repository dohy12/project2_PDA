package pda.server;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey()
    {
        return "current:"+ RoutingDataSourceContextHolder.getClientDatabase();  //연결 요청할 데이터소스를 결정할 Key값을 리턴함
    }
}
