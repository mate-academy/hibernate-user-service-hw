package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String HASH_STRING_FORMAT = "%02x";
    private static final int SALT_LENGTH = 9;

    private HashUtil() {
    }

    public static byte [] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte [] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte [] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte [] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(HASH_STRING_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't create hash using "
                    + CRYPTO_ALGORITHM + " algorithm", e);
        }
        return hashedPassword.toString();
    }
}
