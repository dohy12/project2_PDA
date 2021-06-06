package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Album;

@Mapper
public interface AlbumOperation {

    @Insert("insert into ${GroupId}.picture_list (date, location, title, U_ID, intro) values (\"${Album.getDate()}\", \"${Album.getLocation()}\", \"${Album.getTitle()}\", ${Album.getU_ID()}, \"${Album.getAlbum_intro()}\");")
    public void albumPost(@Param("GroupId") String GroupId, @Param("Album") Album album);
    
    @Select("SELECT LAST_INSERT_ID()")
    public int getAlbumID();

}
