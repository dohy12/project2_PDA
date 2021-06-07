package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pda.server.DAO.AlbumOperation;
import pda.server.DAO.ImageOperation;
import pda.server.DTO.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BoardImage {
    @Autowired
    ImageOperation image;

    @RequestMapping(value = "/BoardImage/{GroupId}", method = RequestMethod.POST)
    public void insertImage(@PathVariable("GroupId") String GroupId, @RequestBody Map<String, Object> params){
        int I_ID = (int)params.get("I_ID");
        String image_src = (String)params.get("image_src");
        int B_ID = (int)params.get("B_ID");

        Image Image = new Image(I_ID, image_src, B_ID);
        image.insertImg(GroupId, Image);

    }

    @RequestMapping(value = "/BoardImage/{GroupId}/{BID}", method = RequestMethod.GET)
    public int imageExist(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID){
        return image.imageExist(GroupId, BID);
    }

    @RequestMapping(value = "/BoardImage/{GroupId}/{BID}/show", method = RequestMethod.GET)
    public List<Image> showImages(@PathVariable("GroupId") String GroupId, @PathVariable("BID") int BID){
        List<Image> imageList = null;

        try{
            imageList = image.showImages(GroupId, BID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageList;
    }

}
