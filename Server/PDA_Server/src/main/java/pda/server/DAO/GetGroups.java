package pda.server.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.group;

import java.util.List;

@Mapper
public interface GetGroups {

    @Select("SELECT * FROM main.GroupNameMapping")
    List<group> getAllGroups();

    @Select("SELECT JoinedGroups FROM main.user${table} where U_ID = #{UID}")
    String getJoinedGroups(@Param("table") int table, @Param("UID") int UID);

    @Select("SELECT AwaitingCertification FROM main.user${table} where U_ID = #{UID}")
    String getAwaitingGroups(@Param("table") int table, @Param("UID") int UID);

    @Select("SELECT Name FROM main.GroupNameMapping where GroupId = #{GroupId}")
    String getGroupName(@Param("GroupId") String GroupId);
}
