package pda.server.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pda.server.Auth.JwtTokenUtil;
import pda.server.DAO.UserInfo;
import pda.server.DTO.User;
import pda.server.DTO.UserPW;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * 인증관련 요청, 로그인, 회원가입 등등
 */

@RestController
@RequestMapping("/auth")
public class AuthCon {

    @Autowired
    UserInfo usr;
    @Autowired
    JwtTokenUtil token;

    @GetMapping()
    public Map<String, Object> Login(@RequestParam String id, @RequestParam String pw)
    {
        UserPW pwinfo = usr.getPW(Integer.toString(id.hashCode() %10), id);
        if(pwinfo == null)
            throw new RestException(HttpStatus.UNAUTHORIZED, "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");

        Map<String, Object> ret = new HashMap<String, Object>();

        if(pwinfo.getPass_Hash().equals(HashingF(pw, pwinfo.getPass_Salt()))) {
            ret.put("result", token.doGenerateToken(pwinfo.getU_ID()));
            return ret;
        }
        else
        {
            throw new RestException(HttpStatus.UNAUTHORIZED, "가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }
    }

    @PostMapping()
    public Map<String, Object> UserCreate(@RequestBody User user)
    {
        Map<String, Object> result = new HashMap<>();

        usr.mainGenerate(user.getId().hashCode()%10, user);
        result.put("result", "성공적으로 만들었습니다.");
        return result;
    }

    @GetMapping("/{ID}")
    public Map<String, Object> IDCheck(@PathVariable String ID)
    {
        if(usr.CheckID(ID.hashCode(), ID) == 0) {
            return (Map<String, Object>) new HashMap<>().put("result","ok");
        }
        else
            return (Map<String, Object>) new HashMap<>().put("result","no");
    }

    public String HashingF(String pw, String salt)
    {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String target = pw + salt;
            byte[] hash = digest.digest(target.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "해싱 함수 오류");
        }
    }
}
