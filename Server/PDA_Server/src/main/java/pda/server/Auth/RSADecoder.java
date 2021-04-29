package pda.server.Auth;

import javax.crypto.Cipher;
import java.util.Base64;

public class RSADecoder {

    public String decryptRSA(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());

        cipher.init(Cipher.DECRYPT_MODE, new RSAKey().getpri("private.der"));
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, "utf-8");
    }
}
