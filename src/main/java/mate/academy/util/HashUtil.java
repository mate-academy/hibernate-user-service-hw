package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static String hashPassword(String password) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b: digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not hash using SHA-512 algorithm", e);
        }
        return hashedPassword.toString();
    }
}
