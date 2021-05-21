package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Message;

import java.util.List;

@Mapper
public interface MessageOperation {

    @Select("select * from ${GroupId}.message where receive_UID = #{UID}")
    List<Message> getRMessages(@Param("GroupId") String GroupId, int UID);

    @Select("select * from ${GroupId}.message where send_UID = #{UID}")
    List<Message> getSMessages(@Param("GroupId") String GroupId, int UID);

    @Insert("insert into ${GroupId}.message(M_ID, receive_UID, send_Date, title, contents, send_UID) value(#{message.receive_UID}, NOW(), #{message.title}, #{message.contents}, #{message.send_UID})")
    void sendMessages(@Param("GroupId") String GroupId, @Param("message")Message message);

    @Delete("delect from ${GroupId}.message where M_ID = #{M_ID}")
    void delectMessage(@Param("GroupId") String GroupId, @Param("M_ID") int M_ID);
}
