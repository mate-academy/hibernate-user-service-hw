package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int SALT_ARRAY_SIZE = 16;

    private HashUtil() {

    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_ARRAY_SIZE];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte bite : digest) {
                hashedPassword.append(String.format("%02x", bite));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't create hash of password using "
                    + CRYPTO_ALGORITHM + "algorithm", e);
        }
    }
}
