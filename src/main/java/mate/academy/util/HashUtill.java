package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtill {

    public HashUtill() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPass = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] digest = md.digest(password.getBytes());
            for (byte b : digest) {
                hashedPass.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could`t create hash algorithm.");
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
