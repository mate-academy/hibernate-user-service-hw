package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int SIZE = 16;
    private static final String FORMAT = "%02x";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] bytes = new byte[SIZE];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    public static String hashPassword(String password, byte[] salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using \""
                    + CRYPTO_ALGORITHM + "\" hashing algorithm", e);
        }
        messageDigest.update(salt);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : messageDigest.digest(password.getBytes())) {
            stringBuilder.append(String.format(FORMAT, b));
        }
        return stringBuilder.toString();
    }
}
