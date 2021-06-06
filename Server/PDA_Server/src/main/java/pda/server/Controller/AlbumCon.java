package pda.server.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pda.server.DAO.AlbumOperation;
import pda.server.DTO.Album;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AlbumCon {

    @Autowired
    AlbumOperation albumOperation;

    @PostMapping("/album/{GroupId}")
    public Map<String, Object> AddAlbum(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try{
            String title = (String)params.get("title");
            String location = (String)params.get("location");
            int u_id = (int)params.get("U_ID");
            String intro = (String)params.get("album_intro");

            String date = (String)params.get("date");

            Album album = new Album(-1, title, date, location, u_id, intro);

            System.out.printf("uid : %d\n",album.getU_ID());
            System.out.printf("loc : %s\n",album.getLocation());
            System.out.printf("date : %s\n",album.getDate());            

            System.out.printf("groupid : %s\n",GroupId);
            albumOperation.albumPost(GroupId, album);
            result.put("result", "앨범 추가 완료");
        }
        catch (Exception e)
        {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "앨범 추가 실패");
        }

        return result;
    }

}
