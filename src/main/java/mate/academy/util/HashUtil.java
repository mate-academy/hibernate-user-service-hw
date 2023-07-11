package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPT_ALGORITHM = "SHA-512";
    private static final String HEX_FORMAT = "%02x";
    private static final int DEFAULT_CAPACITY = 16;

    public HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[DEFAULT_CAPACITY];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPT_ALGORITHM);
            messageDigest.update(salt);
            messageDigest.update(password.getBytes());
            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                builder.append(String.format(HEX_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error while hashing password", e);
        }
        return builder.toString();
    }
}
