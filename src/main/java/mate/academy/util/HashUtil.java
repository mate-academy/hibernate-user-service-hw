package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {
    private static final String SHA_256 = "SHA-256";
    private static final SecureRandom RANDOM = new SecureRandom();

    private HashUtil() {
    }

    public static String getSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        String saltedPassword = salt + password;
        try {
            MessageDigest md = MessageDigest.getInstance(SHA_256);
            byte[] hash = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm '" + SHA_256 + "' not found", e);
        }
    }
}
