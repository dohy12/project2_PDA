package pda.server.DAO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pda.server.Controller.BoardImage;
import pda.server.DTO.Image;

@Mapper
public interface ImageOperation {
    @Insert("insert into ${GroupId}.board_img values(${Image.I_ID}, #{Image.image_src}, ${Image.B_ID})")
    public void insertImg(@Param("GroupId") String GroupId, @Param("Image") Image Image);
}
