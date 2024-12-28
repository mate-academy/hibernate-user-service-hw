package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String HASH_ALGORITHM = "SHA-256";

    public static String hashPassword(String password) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return hash(password, salt);
    }

    public static boolean isValidPassword(String password, String hashedPassword) {
        String salt = hashedPassword.substring(0, 16);
        return hashedPassword.equals(hash(password, salt.getBytes()));
    }

    private static String hash(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return new String(salt) + new String(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such hashing algorithm", e);
        }
    }
}
