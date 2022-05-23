package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password,byte[] salt) {
        StringBuilder saltedHashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte current: digest) {
                saltedHashedPassword.append(String.format("$02x", current));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("failed to generate hash using "
                    + CRYPTO_ALGORITHM + " algorithm", e);
        }
        return saltedHashedPassword.toString();
    }
}
