package pda.server.DAO;

import org.apache.ibatis.annotations.*;
import pda.server.DTO.Album;
import java.util.List;

@Mapper
public interface AlbumOperation {

    @Insert("insert into ${GroupId}.picture_list (date, location, title, U_ID, intro) values (\"${Album.getDate()}\", \"${Album.getLocation()}\", \"${Album.getTitle()}\", ${Album.getU_ID()}, \"${Album.getAlbum_intro()}\");")
    public void albumPost(@Param("GroupId") String GroupId, @Param("Album") Album album);
    
    @Select("SELECT LAST_INSERT_ID()")
    public int getAlbumID();

    @Insert("insert into ${GroupId}.pictures (image_src, P_ID) values (\"${src}\", ${PID})")
    public void insertAlbumPicture(@Param("GroupId") String GroupId, @Param("src") String src, @Param("PID") int P_ID);

    @Select(
        "select E.*, IFNULL(F.comment_cnt, 0) as comment_cnt from " +
        "(select D.*, C.image_src, C.image_cnt from " +
        "(select * from ${GroupId}.picture_list) D " +
        "left join " +
        "(select A.P_ID, A.image_cnt, B.image_src from " +
            "(select min(I_ID) as ID, P_ID, count(*) as image_cnt from ${GroupId}.pictures group by P_ID) A " +
            "left join " +
            "(select I_ID, image_src from ${GroupId}.pictures) B " +
            "on A.ID = B.I_ID) C " +
        "on D.P_ID = C.P_ID) E " +
        "left join (select P_ID, count(*) as comment_cnt from ${GroupId}.picture_comment group by P_ID) F " +
        "on E.P_ID = F.P_ID " +
        "order by P_ID DESC;")
    public List<Album> getAlbumList(@Param("GroupId") String GroupId);

    @Select("select image_src from ${GroupId}.pictures where P_ID = ${P_ID};")
    public String[] getImageList(@Param("GroupId") String GroupId, @Param("P_ID") int P_ID);
}
