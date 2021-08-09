package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int SIZE_OF_SALT_ARRAY = 16;

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte item : digest) {
                hashedPassword.append(String.format("%02x", item));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using "
                    + CRYPTO_ALGORITHM + " algorithm.", e);
        }
        return hashedPassword.toString();
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SIZE_OF_SALT_ARRAY];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
