package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final int DEFAULT_SALT_LENGTH = 16;
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final String HEX_FORMAT = "%02x";

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[DEFAULT_SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(HEX_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn`t find algorithm " + HASH_ALGORITHM, e);
        }
        return hashedPassword.toString();
    }
}
