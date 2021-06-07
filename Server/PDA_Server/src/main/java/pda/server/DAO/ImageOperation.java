package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pda.server.Controller.BoardImage;
import pda.server.DTO.Image;

import java.util.List;

@Mapper
public interface ImageOperation {
    @Select("select * from ${GroupId}.board_img where b_id = ${BID}")
    public List<Image> showImages(@Param("GroupId") String GroupId, @Param("BID") int BID);

    @Insert("insert into ${GroupId}.board_img values(${Image.I_ID}, #{Image.image_src}, ${Image.B_ID})")
    public void insertImg(@Param("GroupId") String GroupId, @Param("Image") Image Image);

    @Select("select count(*) as res from ${GroupId}.board_img where b_id = ${BID}")
    public int imageExist(@Param("GroupId") String GroupId, @Param("BID") int BID);
}
