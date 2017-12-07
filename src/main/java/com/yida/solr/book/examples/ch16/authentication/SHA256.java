package com.yida.solr.book.examples.ch16.authentication;

import org.apache.commons.codec.binary.Base64;
import org.apache.solr.security.BasicAuthPlugin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Lanxiaowei
 * SHA-256密码加密
 */
public class SHA256 {

    public static void main(String[] args) {
        //原始明文密码
        String pwd = "solr";
        pwdEncrypt(pwd);
    }
    //对密码进行SHA-256密码加密
    public static String pwdEncrypt(String pwd) {
        System.out.println("加密前密码：" + pwd);
        Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        String saltBase64 = Base64.encodeBase64String(salt);
        String val = sha256(pwd, saltBase64) + " " + saltBase64;
        System.out.println("加密后密码：" + val);
        return val;
    }

    public static String sha256(String password, String saltKey) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        if (saltKey != null) {
            digest.reset();
            digest.update(Base64.decodeBase64(saltKey));
        }
        byte[] btPass = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        digest.reset();
        btPass = digest.digest(btPass);
        return Base64.encodeBase64String(btPass);
    }
}
