package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String STRING_FORMAT = "%02x";
    private static final int DEFAULT_SALT_CAPACITY = 15;

    public HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[DEFAULT_SALT_CAPACITY];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashBuilder.append(String.format(STRING_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create using hash", e);
        }
        return hashBuilder.toString();
    }
}
