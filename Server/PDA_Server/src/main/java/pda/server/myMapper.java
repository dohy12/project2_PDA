package pda.server;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface myMapper {

    //@Select("SELECT pass_Hash, pass_Salt FROM user WHERE ID = #{id}")
    //Map<String, Object> getHashAndSalt(String id);

    @Select("SELECT * FROM test")
    String test();
}
