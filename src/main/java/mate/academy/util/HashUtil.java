package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    public static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] bytes = new byte[16];
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
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
