package pda.server.Auth;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import java.security.*;
import java.security.spec.*;

public class RSAKey {
    public PrivateKey getpri(String filename)
            throws Exception {

        ClassPathResource cpr = new ClassPathResource(filename);
        byte[] keyBytes = FileCopyUtils.copyToByteArray(cpr.getInputStream());

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public PublicKey getpub(String filename) throws Exception {
        ClassPathResource cpr = new ClassPathResource(filename);
        byte[] keyBytes = FileCopyUtils.copyToByteArray(cpr.getInputStream());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
