package pda.server;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import pda.server.Auth.RSADecoder;
import pda.server.Auth.RSAKey;

import javax.crypto.Cipher;
import java.util.Base64;
import java.util.UUID;

@SpringBootTest
class ServerApplicationTests
{
    @Value("${spring.datasource.password}")
    private String PW;
    @Value("${jasypt.encryptor.password}")
    private String KEY;
    @Value("${spring.jwt.secret}")
    private String sk;

    @Test
    void pw()
    {
        System.out.println(PW);
        System.out.println(KEY);
        System.out.println(sk);
        // 创建加密对象，默认 PBEWithMD5AndDES
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        // 加密所需的密钥
        textEncryptor.setPassword("PDA_Server");
        // 加密后的数据（数据库的用户名或密码）
        String encData = textEncryptor.encrypt(PW);
        // 解密后的数据（原数据）
        String decData = textEncryptor.decrypt(encData);
        System.out.println("encData: " + encData);
        System.out.println("decData: " + decData);
    }

    @Value("${spring.datasource.password}")
    String mysqlPW;
    @Autowired
    Environment environment;

    @Test
    void getSetting()
    {
        System.out.println(mysqlPW);
    }

    @Test
    void groupIdGeneration()
    {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24); //'-' 기호를 삭제
        System.out.println(temp);
    }

    @Test
    void RSATest() throws Exception {
        RSAKey key = new RSAKey();

        System.out.println(key.getpri("private.der").toString());
        System.out.println(key.getpub("public.der").toString());
        Cipher cip = Cipher.getInstance("RSA");
        cip.init(Cipher.ENCRYPT_MODE, key.getpub("public.der"));
        byte[] en = cip.doFinal("테스트입니다".getBytes());

        System.out.println(new RSADecoder().decryptRSA(Base64.getEncoder().encodeToString(en)));
    }
}

