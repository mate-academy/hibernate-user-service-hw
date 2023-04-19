package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SaltUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private SaltUtil() {

    }

    public static String getSalt(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return hashedPassword.toString();
    }
}
