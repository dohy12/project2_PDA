package pda.server.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface testMapper {

    //test용 쿼리문
    @Select("SELECT * FROM ${dbname}.test")
    Map<String, Object> test(@Param("dbname") String dbname);
}
