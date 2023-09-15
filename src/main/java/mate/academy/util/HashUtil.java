package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASHING_ALGORITHM = "SHA-512";
    private static final int DEFAULT_SALT_LENGTH = 16;

    private HashUtil() {
    }

    public static String hash(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            messageDigest.update(salt);
            byte[] encrypted = messageDigest.digest(password.getBytes());
            StringBuilder res = new StringBuilder();
            for (byte b : encrypted) {
                res.append(String.format("%02x", b));
            }
            return res.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Invalid hashing algorithm: " + HASHING_ALGORITHM, e);
        }
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[DEFAULT_SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}
