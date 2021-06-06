package pda.server.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.group;

import java.util.List;
import java.util.Map;

@Mapper
public interface GetGroups {

    @Select("SELECT * FROM main.GroupNameMapping")
    List<group> getAllGroups();

    @Select("SELECT JoinedGroups FROM main.user${table} where U_ID = #{UID}")
    String getJoinedGroups(@Param("table") int table, @Param("UID") int UID);

    @Select("SELECT AwaitingCertification FROM main.user${table} where U_ID = #{UID}")
    String getAwaitingGroups(@Param("table") int table, @Param("UID") int UID);

    @Select("SELECT Name, GroupImg FROM main.GroupNameMapping where GroupId = #{GroupId}")
    group getGroupInfos(@Param("GroupId") String GroupId);

    @Select("select GroupId, GroupImg from GroupNameMapping where Name = #{Name}")
    group getAGroupInfos(@Param("Name") String Name);
}
