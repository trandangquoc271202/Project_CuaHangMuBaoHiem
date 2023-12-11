package vn.edu.hcmuaf.fit.DigitalSignature;

import vn.edu.hcmuaf.fit.Database.DBConnect;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.model.Product;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DigitalSignature {
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

    public String encryptRSA(String text, PrivateKey privateKey) {
        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            output = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(output);
    }

    public String decryptRSA(String text, PublicKey publicKey) {
        Cipher cipher = null;
        byte[] output = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            output = cipher.doFinal(Base64.getDecoder().decode(text));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return new String(output, StandardCharsets.UTF_8);
    }

    public PrivateKey importPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance("RSA");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    public PublicKey importKeyPublic(String publicKey) {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
        KeyFactory keyFactory = null;
        X509EncodedKeySpec publicKeySpec = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {

        }
        return null;
    }

    public String getInformationOrder(String username, String phoneNumber, String address, Cart cart) {
        long totalCost = 0;
        String result = "";
        result += username + "\n" + phoneNumber + "\n" + address + "\n";
        Product product = null;
        for (String key : cart.getCart().keySet()) {
            product = cart.getCart().get(key);
            result += product.getId() + " " + product.getDetail().get(0).getId() +
                    " " + product.getDetail().get(0).getSize() +
                    " " + product.getDetail().get(0).getColor() + " "
                    + product.getName() + " " + product.getQuantity() + " " + (product.getPrice() - (product.getDiscount() * product.getPrice())) + "\n";
            totalCost += (product.getPrice() - (product.getDiscount() * product.getPrice())) * product.getQuantity();
        }
        result += totalCost;
        return result;
    }

    public void downloadFile(String destFile) {

    }

    public int getPublicKey(String id_user) {
        DBConnect dbConnect = DBConnect.getInstance();
        try {
            PreparedStatement ps = dbConnect.getConnection().prepareStatement("select id from public_key where id_kh =? AND status= ? ");
            ps.setString(1, id_user);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getInt("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        DigitalSignature digitalSignature = new DigitalSignature();
        String hashThongDiep = digitalSignature.hashString("Trần Đặng Quốc");
//        digitalSignature.importKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCg02ZTtWB4bsNdKOToPx6fum5Bhnt987uMxRTjnBQJ14hhN00EBV7FMDSqqHUarsGwWUh4yZRY7VS71Q2DVFEmLL3v++7q9ra78QVPw5fTexEuyMbV+p8OpC0zluNbvUW6RxynJylRojbHP/yqFbCnCxAA+bTsheZIo4UtvlqQYWqTLrVzBtgpsZbOK87D3BW5C8JIr61rrTeY/v4+zHjW7B9qX1izzdgQOtLo3SGoZiAaHbDkNfXmnrRFe0cgJuuBtc1KDhMy4cTbw+dZtPpY+pKuc2MOnePk5IKRuaj5qek8eDIc+A55GKat84Ax04qsdJCGcoGQxD3eMkyB5vXTAgMBAAECggEAEQRdWvUhqIybymM12Wc4X9FTskY/EeQ0z5/Y5n6Q1jvCl/rIgOjqjrXblMUvYuvj3buMwg8eTcyK/zb7b45/6aipTlfbf2wsiLVniPmauotWlZs0Atc6dkuj5sYL95yO4Ld+jidEJXmjDzEzlHGoF/zBLEE/xhki9UOsJ1txbL5e5TJc6716IG05upwTYht/JM+DVCq0uUKRl8Cvzq2k9FDo3NzNpjd4sg8LJHg8Bd7oWMrAhhDu2mONXnPu6cngIuehlULieSN5GomZA1XK1Y85HPsaolljcSqKSSUge4dglE43xLmxhYAdsqsMA+fN8Z6weu9hOJjfB/Tzt51X2QKBgQDgrlrYVOUaenK8w+8JJs2UeTlIslgRA3uVwpgAo+Uf9AAheDcDirx0pYFh7MxMQYuMNuKeE0L2jmeoeyJQXXxdcEz/i9k0nFtEoC5I+VHyC7dxp/A6yUy2MqMKmkHl88vBZDFf3QcYlHTKVmlkwm996WyRZrNq+GIIBO8Jotb82QKBgQC3PmWWtqOekTNdZXKdjUwvBiuyp3OwtmjwZzeDak3vLSqrkwj9Y8xWrfDwPJBmbQDHUutI1REpWBbdDbDxFeJ345qpDTW1vRZIfObCk3M210rR3j8YyS7tZ1CuHeuCirfjJ31Aoh+A6jWen/rTd6q22+0/Ih1B5ksTwyZw8O2MiwKBgE4eENPaYHWeUTo0XW2vIhcXNsWBzIDN1zAwoizGzykW6ty4MVh36iIcUSm87SMuPtiXVJ9lTwjuZjf75+hNOKo8G2lWNETIgCsSDkWs+cLQNA2+OBYYWyWV4X4UOAk9hIBO16Pe81KmexzGgopX1+/NMsQzEvqENYFYRcvx5BGxAoGBAIxRqqdLemWtWMD3M1p0XddQiVvIIK48MIWO9JKcb8tsMdWqxWKL7midz/eW2Ril9cestmFST07ylqd1mX4zvPUDhGZZqDxf7FVb17U+BicDm9azsmt/e4Dt8Xkzjimrof6/OYmp3CfysVajuQYAB+j45mbG79KL0Af1E1W/GJjbAoGAeLn8ODk2t6R2GC8hpvgnPMB+7WkoMn2jhzFPD5GK68ToBvKCKyNY8g3hcG/CsYODZ2IVUuT8NbYd/VlPzGy702TgjR6FjWUSZQu+UhpZ/Pm91NTkgln4HwJEne7YwIhTI83WUgrLWELyLcxftFffu4kwvqYzVWzlrio8wKk/XKk=", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoNNmU7VgeG7DXSjk6D8en7puQYZ7ffO7jMUU45wUCdeIYTdNBAVexTA0qqh1Gq7BsFlIeMmUWO1Uu9UNg1RRJiy97/vu6va2u/EFT8OX03sRLsjG1fqfDqQtM5bjW71FukccpycpUaI2xz/8qhWwpwsQAPm07IXmSKOFLb5akGFqky61cwbYKbGWzivOw9wVuQvCSK+ta603mP7+Psx41uwfal9Ys83YEDrS6N0hqGYgGh2w5DX15p60RXtHICbrgbXNSg4TMuHE28PnWbT6WPqSrnNjDp3j5OSCkbmo+anpPHgyHPgOeRimrfOAMdOKrHSQhnKBkMQ93jJMgeb10wIDAQAB");
        String encryptHash = digitalSignature.encryptRSA(hashThongDiep, digitalSignature.importPrivateKey("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCsJGprRwpDLff9JnxvLnZjm+pk7HBMrb6hiC+BFb7CmiIxzswEuqmFMBb/83wR3mpO7QmZmF839FNpDE45gkMmZfMEX21oBQFCj0zjkYFxIYTVHBYrPdxKVJMQFDEYkDpZiBuvh9fchQFN/vUM9tsW8Y3lOXrATXwEvdvVbCnlJiIuGwo7qITzhOWtL3QL3JcVbNHUSqrUyZ9cVpKktAzc+LcrM9liYIdDA4zT4G6VnPd7rvHRJ9AJWrLGCfO/V3FHztQ3FlOuwzPgifQhmHV4oJb6+e+M3yLo8mfPROiN8KRnZvqrdaeg2K4FIkij+OPcbVphwJmzaqRAxMnynTFVAgMBAAECggEAI54FRt0UCoJgZUipRnfelw2Tmf4x3iu03cdHzPWq2qj5mkSngfL9UEl6+MpwSWIzrNDuw5D7YqBzAbFCHjQNoMxHVAhKotMmR5dO2Wcp2TaOPWnqTFgoVFjbdOA4qFRvPkcjM1ZwG3iJpJG0MlKBUmX4bg1OkKt3laOS8L3IjVjXn5M9l0VmSH4WMwQJCRPPINd9AuDhG5bPbIA4/F50hpviVMvxUA9ZJc998Y376zUBkxKMdWdN+NQSbRjo1rmROdpZOUHO+dcC3GdJXo536sRlcQlKQtabJdiGftugzS/p3I/IfjRkjc3aKyd2D8Z898ZEO/5G0NmkfXKIUUnAoQKBgQDYreIEmRMuoiIPQnJ1gZyqgu00/wyR2n3Mo8Wcg8FIZaSN9gLeU4H7uuoqCfu5f7ZVa7jIlz8Qpf8SabJChRcrCKii4hE9PNf9WJFPv9w8avVK7x4HqWWBcR3N+Kd2llySFTtA9A55/k60sBwh3KQJYIYgrzpDPFDZOZA1xnRIuwKBgQDLYYJThToCO44OuzJFIG9+1bvqRJfi+Yrl1u4yJya0qcQO6k/Tts4PSRtHR+5rz/SWu8I2+JeUV2NEgsr4EZPygpfcr5UEZzvGjTeDf1c2AmlKvEnyO1wQ/zy7OmhJxu61gKhiASppQFKLqd+dgSqgN+CnweH2H35HpagpK12VLwKBgEknO31t6Mn/txwLxPTjE2+F0GvqB+ZeK8ahLCBRYzn8BvherE0/SE0ip9gVksTn1zkzqykd1w7Z3CntQV+v60h+HeEQDd3s4scH1ddk4QgM3E+2Nx9DE6AF/pgBcRhaX1p6jIy7WP/a2duq8XSFN2RrEB2My1DT1j/gbU2aeLvtAoGAefmkGQU48ifXnfwlPRIj7FSxW+IBme4BoLDTsJ6MdyOt4ygh8h6b3M0CoDCCzIQu88vtwdw7xIrYjTlIE5kvOu2ZuJBRdg+X6Q++sI3JtymhDmJ4kt+I6uS4/q0b6Mt3VMGxuv1p+y/JkduyshrKfsakZNhB9cOTj9LJVM+eU58CgYA80hOdG9LBe4ZqSmPt84FDthMYWSOPQ9qIJ504tEx9f+tHle7QGkOLljZdHH8VENiDcW1eyUH+sIw6pkEMwKnYP8aU52DOTgAZHCleClQ1Hsxo5haByFpplbVLwRLFasmN2CBwuRoxYDKmgPPmHXK0ISSzopJmmlOr5agRhbqhrg=="));
        System.out.println(encryptHash);
//        String decryptHash = digitalSignature.decryptRSA(encryptHash);
//        System.out.println(hashThongDiep.equals(decryptHash));
        System.out.println(digitalSignature.getPublicKey("1"));;
    }
}
