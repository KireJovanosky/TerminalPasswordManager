package com.passManager.service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static java.lang.String.valueOf;

public class PasswordGenerator {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{};:',./?`~";
    private final String keyValue = "ExampleString@";

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();
        String password = "";
        String encryptedPassword = null;

        for (int i = 0; i < length; i++){
            int index = random.nextInt(CHAR_POOL.length());
            password = valueOf(passwordBuilder.append(CHAR_POOL.charAt(index)));
        }
         encryptedPassword = encrypt(password, setKey256Bit(keyValue));

         // Clear the unencrypted password from memory
         Arrays.fill(password.toCharArray(), '\0');

         return encryptedPassword;
    }

    public String userDefinedPassword(String password) {
        String encryptedPassword = null;
            encryptedPassword = encrypt(password, keyValue);

            // Clear the unencrypted password from memory
            Arrays.fill(password.toCharArray(), '\0');

            return encryptedPassword;
    }

    public static String setKey128Bit(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-256"); //SHA-256 for 128-bit key
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // 128 bits
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myKey;
    }

    public static String setKey256Bit(final String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-256"); //SHA-256 for 256-bit key
            key = sha.digest(key);
            key = Arrays.copyOf(key, 32); // 256 bits
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return myKey;
    }

    public static String encrypt(final String strToEncrypt, final String secret) {
        try {
            setKey256Bit(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return null;
    }

    public static String decrypt(final String strToDecrypt, final String secret) {
        if (strToDecrypt != null) {
            try {
                setKey256Bit(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            } catch (Exception e) {
                System.out.println("Error while decrypting: " + e);
            }
        }
        return null;
    }

    public String getKeyValue() {
        return keyValue;
    }
}
