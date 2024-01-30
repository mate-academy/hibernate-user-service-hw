package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int SALT_ARRAY_LENGTH = 16;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_ARRAY_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    public static String getHashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using " + CRYPTO_ALGORITHM
                    + " algorithm");
        }
        return hashedPassword.toString();
    }
}
