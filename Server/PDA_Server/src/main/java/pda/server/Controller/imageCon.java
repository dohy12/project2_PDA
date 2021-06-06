package pda.server.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import pda.server.DAO.AlbumOperation;

@RestController
public class imageCon {
    
    
    @Autowired
    AlbumOperation albumOperation;

    @PostMapping("/image/{src}")
    public Map<String, Object> saveImage(@PathVariable String src, @RequestParam("image") MultipartFile file)
    {
        Map<String, Object> m = new HashMap<>();
        try{
            InputStream in = file.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, "png", new File("/home/ubuntu/Server/images/"+src));            
            throw new RestException(HttpStatus.OK, "이미지 저장 성공");
        } catch (IOException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 실패");
        } catch (RestException e) {
            throw e;
        }
    }

    @PostMapping("/image_album/{GroupId}/{A_ID}/{img_num}")
    public Map<String, Object> saveImage_album(@PathVariable("GroupId") String groupId, @PathVariable("A_ID") int A_ID, @PathVariable("img_num") int img_num, @RequestParam("image") MultipartFile file)
    {
        String src = "album_" + A_ID + "_" + img_num + ".png";
        
        Map<String, Object> m = new HashMap<>();
        try{
            InputStream in = file.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, "png", new File("C:/Users/User/Documents/GitHub/project2_PDA/Server/PDA_Server/images/"+src));
            //ImageIO.write(bufferedImage, "png", new File("/home/ubuntu/Server/images/"+src)); 잠시 로컬에서 테스트 중입니다.
            
            albumOperation.insertAlbumPicture(groupId, src, A_ID);

            throw new RestException(HttpStatus.OK, "이미지 저장 성공");            
        } catch (IOException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 실패");
        } catch (RestException e) {
            throw e;
        }
    }
}