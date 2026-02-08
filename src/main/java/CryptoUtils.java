package com.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {
    private static final String ALGO = "AES/CBC/PKCS5Padding"; // Correct: CBC with padding [page:0]
    private static final byte[] FIXED_IV = "1234567890123456".getBytes(StandardCharsets.UTF_8); // Poor: Fixed IV reuse [web:1][web:6]
    
    // Poor key derivation: Direct password bytes (no PBKDF2/salt) [page:1][web:3]
    public static SecretKeySpec getKey(String password) {
        byte[] keyBytes = password.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 16) {
            byte[] fixed = new byte[16];
            System.arraycopy(keyBytes, 0, fixed, 0, Math.min(16, keyBytes.length));
            keyBytes = fixed;
        }
        return new SecretKeySpec(keyBytes, "AES"); // AES-128 [page:0]
    }
    
    public static void encrypt(File in, File out, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(FIXED_IV));
        
        try (FileInputStream fis = new FileInputStream(in);
             FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(cipher.update(buffer, 0, len));
            }
            fos.write(cipher.doFinal());
        }
    }
    
    public static void decrypt(File in, File out, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(FIXED_IV));
        
        try (FileInputStream fis = new FileInputStream(in);
             FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(cipher.update(buffer, 0, len));
            }
            fos.write(cipher.doFinal());
        }
    }
    
    public static String computeSHA256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Integrity check [web:13][web:15]
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            return bytesToHex(digest.digest());
        }
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
