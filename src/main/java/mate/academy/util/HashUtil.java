package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String EXCEPTION_CREATE_HASH_MESSAGE =
            "Failed to create a hash using the SHA-512 algorithm";
    private static final String FORMAT_SPECIFIER = "%02X";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b: digest) {
                hashedPassword.append(String.format(FORMAT_SPECIFIER, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    EXCEPTION_CREATE_HASH_MESSAGE, e);
        }
        return hashedPassword.toString();
    }
}
