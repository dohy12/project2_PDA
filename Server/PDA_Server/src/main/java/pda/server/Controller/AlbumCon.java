package pda.server.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.AlbumOperation;
import pda.server.DTO.Album;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlbumCon {

    @Autowired
    AlbumOperation albumOperation;

    @PostMapping("/album/{GroupId}")
    public Map<String, Object> AddAlbum(@PathVariable("GroupId") String GroupId, @RequestBody Album body) {
        Map<String, Object> result = new HashMap<>();
        try{
            albumOperation.albumPost(GroupId, body);
            result.put("result", "앨범 추가 완료");
        }
        catch (Exception e)
        {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "앨범 추가 실패");
        }

        return result;
    }

}
