package pda.server.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@Controller
public class imageCon {
    @GetMapping("/image/{src}")
    public byte[] getImage(@PathVariable String src) throws IOException {

        BufferedImage originalImage = ImageIO.read(new File(src));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos);
        baos.flush();

        byte[] imageByte = baos.toByteArray();

        return imageByte;
    }

    @PostMapping("/image/{src}")
    public Map<String, Object> saveImage(@PathVariable String src, @RequestParam("image") MultipartFile file)
    {
        Map<String, Object> m = new HashMap<>();
        try{
            InputStream in = file.getInputStream();
            BufferedImage bufferedImage = ImageIO.read(in);
            ImageIO.write(bufferedImage, "png", new File("images/"+src));
            throw new RestException(HttpStatus.OK, "이미지 저장 성공");
        } catch (IOException e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 실패");
        } catch (RestException e) {
            throw e;
        }
    }
}