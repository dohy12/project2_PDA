package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Message;

import java.util.Map;

@Mapper
public interface MessageOperation {

    @Select("select * from ${GroupId}.message where receive_UID = #{UID}")
    Message[] getRMessages(@Param("GroupId") String GroupId, int UID);

    @Select("select * from ${GroupId}.message where send_UID = #{UID}")
    Message[] getSMessages(@Param("GroupId") String GroupId, int UID);

    @Insert("insert into ${GroupId}.message(M_ID, receive_UID, send_Date, title, contents, send_UID) value(#{receive_UID}, NOW(), #{title}, #{contents}, #{send_UID})")
    void sendMessages(@Param("GroupId") String GroupId, @Param("send_UID") int send_UID, @Param("receive_UID") int receive_UID, @Param("title") String title, @Param("contents") String contents);

    @Delete("delect from ${GroupId}.message where M_ID = #{M_ID}")
    void delectMessage(@Param("GroupId") String GroupId, @Param("M_ID") int M_ID);
}
