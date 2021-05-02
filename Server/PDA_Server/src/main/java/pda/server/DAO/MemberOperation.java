package pda.server.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pda.server.DTO.Member;

import java.util.Map;

@Mapper
public interface MemberOperation
{
    @Select("select * from ${GroupID}.user ")
    public Member[] MemberList(@Param("GroupID") String GroupID);

    @Select("select * from main.user${Num} where U_ID = #{UID}")
    public UserInfo details(@Param("Num") int Num, @Param("UID") int UID);

    @Select("select isAdmin from ${GroupID}.user where U_ID = #{UID} ")
    public int isAdmin(@Param("GroupID") String GroupID, @Param("UID") int UID);

    @Update("UPDATE ${GroupID}.user set isAdmin = #{AdminFlag};")
    public int setAdmin(@Param("GroupID") String GroupID, @Param("AdminFlag") int AdminFlag);

    @Select("select U_ID from ${GroupID}.user where JoinTime is null ")
    public Map<String,Object> waitingToJoin(@Param("GroupID") String GroupID);
}
