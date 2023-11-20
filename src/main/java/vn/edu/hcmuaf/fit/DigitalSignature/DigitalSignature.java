package vn.edu.hcmuaf.fit.DigitalSignature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class DigitalSignature {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public String hashString(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] output = md.digest(text.getBytes());
            BigInteger num = new BigInteger(1, output);
            return num.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String encryptRSA(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] output = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(output);
    }
    public String decryptRSA(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(output,StandardCharsets.UTF_8);
    }
    public void importPrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
    }
    public boolean importKey(String privateKey, String publicKey){
        try{
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            this.publicKey = keyFactory.generatePublic(publicKeySpec);
            try{
                byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                this.privateKey = keyFactory.generatePrivate(privateKeySpec);
                return true;
            }catch (Exception e){

                return false;
            }
        }catch (Exception e){

            return false;
        }
    }
    public void downloadFile(String destFile){

    }
    public static void main(String[] args) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        DigitalSignature digitalSignature = new DigitalSignature();
        String hashThongDiep = digitalSignature.hashString("Trần Đặng Quốc");
        digitalSignature.importKey("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");
        String encryptHash = digitalSignature.encryptRSA(hashThongDiep);
        System.out.println(encryptHash);
        String decryptHash = digitalSignature.decryptRSA(encryptHash);
        System.out.println(hashThongDiep.equals(decryptHash));
    }
}
