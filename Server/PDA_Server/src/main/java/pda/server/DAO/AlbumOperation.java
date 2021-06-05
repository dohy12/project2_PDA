package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Album;

@Mapper
public interface AlbumOperation {
    @Insert("insert into ${GroupId}.picture_list values(${Album.P_ID}, ${Album.date}, \"${Album.location}\", \"${Album.title}\", \"${Album.U_ID}\", ${Album.intro})")
    public void albumPost(@Param("GroupId") String GroupId, @Param("Album") Album Album);
}
