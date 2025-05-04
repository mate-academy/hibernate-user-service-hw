package mate.academy.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASHING = "SHA-512";
    private static final String FORMAT = "%02";
    private static final int SIZE = 16;

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashPass = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING);
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes());
            for (byte b : bytes) {
                hashPass.append(String.format(FORMAT, b));
            }
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create hash", e);
        }
        return hashPass.toString();
    }
}
