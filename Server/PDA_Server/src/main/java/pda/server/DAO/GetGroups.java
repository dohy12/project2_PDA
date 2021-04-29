package pda.server.DAO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pda.server.DTO.group;

import java.util.List;

@Mapper
public interface GetGroups {

    @Select("SELECT * FROM main.GroupNameMapping")
    List<group> get();

}
