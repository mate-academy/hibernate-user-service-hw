package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String HEX_FORMAT = "%02x";
    private static final int DEFAULT_CAPACITY = 16;

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[DEFAULT_CAPACITY];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte oneByte : digest) {
                builder.append(String.format(HEX_FORMAT, oneByte));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash using SHA-512 algorithm", e);
        }
        return builder.toString();
    }
}
