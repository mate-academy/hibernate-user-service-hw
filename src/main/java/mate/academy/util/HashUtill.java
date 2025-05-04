package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtill {
    private static final String CRYPTO_KEY = "SHA-512";

    private HashUtill() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPass = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(CRYPTO_KEY);
            md.update(salt);
            byte[] digest = md.digest(password.getBytes());
            for (byte element : digest) {
                hashedPass.append(String.format("%02x", element));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could`t create hash algorithm.", e);
        }
        return hashedPass.toString();
    }

    public static byte[] getSalt() {
        SecureRandom secure = new SecureRandom();
        byte[] salt = new byte[16];
        secure.nextBytes(salt);
        return salt;
    }
}
