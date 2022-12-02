package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String HEX_STRING = "%02x";
    private static final int SALT_LENGTH = 16;

    private PasswordUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String getHash(String password, byte[] salt) {
        StringBuilder builder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                builder.append(String.format(HEX_STRING, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Couldn't get hash "
                    + "for password using " + HASH_ALGORITHM, e);
        }
        return builder.toString();
    }
}
