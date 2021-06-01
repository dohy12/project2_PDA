package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Member;
import pda.server.DTO.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberOperation
{
    @Select("select * from ${GroupID}.user ")
    public Member[] MemberList(@Param("GroupID") String GroupID);

    @Select("select * from main.user${Num} where U_ID = #{UID}")
    public Member details(@Param("Num") int Num, @Param("UID") int UID);

    @Select("select isAdmin from ${GroupID}.user where U_ID = #{UID} ")
    public int isAdmin(@Param("GroupID") String GroupID, @Param("UID") int UID);

    @Update("UPDATE ${GroupID}.user set isAdmin = #{AdminFlag};")
    public void setAdmin(@Param("GroupID") String GroupID, @Param("AdminFlag") int AdminFlag);

    @Select("select U_ID from ${GroupID}.user where JoinTime is null ")
    public List<Integer> waitingToJoin(@Param("GroupID") String GroupID);

    @Select("select JoinedGroups from main.user${Num} where U_ID = #{UID}")
    public String JoinedGroups(@Param("Num") int Num, @Param("UID") int UID);

    @Select("select AwaitingCertification from main.user${Num} where U_ID = #{UID}")
    public String AwaitingCertification(@Param("Num") int Num, @Param("UID") int UID);

    @Update("UPDATE main.user${Num} set JoinedGroups = #{Groups} where U_ID = #{UID}")
    public void UpdateJoinedGroups(@Param("Num") int Num, @Param("Groups") String Groups, @Param("UID") int UID);

    @Update("UPDATE main.user${Num} set AwaitingCertification = #{Groups} where U_ID = #{UID}")
    public void UpdateAwaitingCertification(@Param("Num") int Num, @Param("Groups") String Groups, @Param("UID") int UID);

    @Update("UPDATE main.user${Num} set AwaitingCertification = null where U_ID = #{UID}")
    public void UpdateAwaitingCertificationToNull(@Param("Num") int Num, @Param("UID") int UID);

    @Update("UPDATE main.user${Num} set JoinedGroups = null where U_ID = #{UID}")
    public void UpdateJoinedGroupsToNull(@Param("Num") int Num, @Param("UID") int UID);

    @Insert("INSERT ${GroupID}.user(U_ID,ID) values (#{UID},#{ID})")
    public void AddUser(@Param("GroupID") String GroupID, @Param("UID") int UID, @Param("ID") String ID);

    @Select("select ID from main.user${Num} where U_ID = #{UID}")
    public String FindIDByUid(@Param("Num") int Num, @Param("UID") int UID);

    @Select("select * from ${GroupID}.user where JoinTime is null")
    public List<Member> WaitingList(@Param("GroupID") String GroupID);

    @Update("UPDATE ${GroupID}.user set JoinTime = now() where U_ID = #{UID} ")
    public void Certification(@Param("GroupID") String GroupID,@Param("UID") int UID);
}
