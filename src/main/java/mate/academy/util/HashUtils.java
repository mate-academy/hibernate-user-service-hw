package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtils {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int DEFAULT_LENGTH = 16;
    private static final String FORMAT_SPECIFIER = "%02x";

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
            for (byte bt : digest) {
                hashedPassword.append(String.format(FORMAT_SPECIFIER, bt));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Coudnt create hash using SHA-512 algirithm ",e);
        }
        return hashedPassword.toString();
    }
}
