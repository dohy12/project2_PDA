package com.example.pda;

import javax.crypto.Cipher;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

public class RSAEncoder {

    public String EncryptRSA(String plain) throws Exception {
        File f = new File("public.der");
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Cipher cip = Cipher.getInstance("RSA");
        cip.init(Cipher.ENCRYPT_MODE, kf.generatePublic(spec));
        byte[] en = cip.doFinal(plain.getBytes());
        return Base64.getEncoder().encodeToString(en);
    }
}
