package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.User;
import pda.server.DTO.UserPW;

@Mapper
public interface UserInfo {

    //패스워드 솔트와 해쉬값을 가져온다
    @Select("Select U_ID, name, pass_Hash, pass_Salt, profileImg from main.user${table} where ID = #{ID}")
    UserPW getPW(@Param("table") String table, @Param("ID") String ID);

    @Select("Select COUNT(*) from main.user${table} where ID = #{ID}")
    int CheckID(@Param("table") int table, @Param("ID")  String ID);

    /*
    @Select("Select  from ${GroupId}.user where ID = ${ID}")
    List<UserInfo> getUserInfo();
    */
//
    @Insert("insert into main.user${table}(name, ID, pass_Hash, pass_Salt, age, birth, email, kakao, phone, profileImg, introduction, JoinedGroups, AwaitingCertification) " +
            "values(#{user.name}, #{user.id}, #{user.passHash}, #{user.passSalt}, #{user.age}, #{user.birth}, #{user.email}, #{user.kakao}, #{user.phone}, #{user.profileimg}, #{user.introduction}, #{user.joinedgroups}, #{user.awaitingcertification})")
    void mainGenerate(@Param("table") int table, @Param("user")User user);
}
