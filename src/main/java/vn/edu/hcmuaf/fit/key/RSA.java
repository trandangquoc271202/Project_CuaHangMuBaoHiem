package vn.edu.hcmuaf.fit.key;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {

    public KeyPair generateKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return  keyPairGenerator.generateKeyPair();
    }

    public String encryptRSA(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(output);
    }

    public String decryptRSA(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] output = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(output, StandardCharsets.UTF_8);
    }

    public String keyToString(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    public PublicKey convertStringToPublicKey(String publicKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
    public PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws Exception {
        RSA rsa = new RSA();
        KeyPair keyPair = rsa.generateKey();
        System.out.println(rsa.keyToString(keyPair.getPublic()));
        System.out.println(rsa.keyToString(keyPair.getPrivate()));
        if (rsa.keyToString(keyPair.getPublic()).equals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApMOj5Fi+4pyaKAapDSV8RjJzgh0C3wdhvKsj3+zMoTDxI8+OSCMqvsl082w4VxBBkMNnw++Knr6jBHihrDHmmAgm6T+7d0eEzwJN5uhQIJznj26uyn+Q3asB2gXuYeMYm2n2pLgXBWyTmgjQMpUB6h/3xs5YHd+cINpqBZVfkYiobnGtO5J16F3jjfb3YbUyakfZOrPaBxKLmQbHK8IZYP6Y37K5A/IhVyqHc04kTeHa0dqexNtxNZVizZ0o2YNh42A8lyDM+wea/5KK3h+QY2pOXWZc0o6E+0TKaBvg00BKMXC6RDfF7k+lbTH18Oq6h6Arzjrr8kSnxI+suozvpQIDAQAB")) {
            System.out.println("Giống");
        } else {
            System.out.println("Khác");
        }
    }
}
