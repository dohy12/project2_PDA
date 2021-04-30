package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.UserPW;
import java.util.List;

@Mapper
public interface User {

    //패스워드 솔트와 해쉬값을 가져온다
    @Select("Select U_ID ,pass_Hash, pass_Salt from main.user${table} where ID = \"${ID}\"")
    UserPW getPW(String table, String ID);

    /*
    @Select("Select  from ${GroupId}.user where ID = ${ID}")
    List<UserInfo> getUserInfo();
    */

}
