package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String ALGORITHM_ERROR_MSG =
            "Could not create hash using SHA-512 algorithm";
    private static final String STRING_FORMAT = "%02x";
    private static final int DEFAULT_LENGTH = 16;

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[DEFAULT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte unit: digest) {
                hashedPassword.append(String.format(STRING_FORMAT, unit));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(ALGORITHM_ERROR_MSG, e);
        }
        return hashedPassword.toString();
    }
}
