package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String ENCRYPTION = "SHA-512";
    private static final String BYTE_FORMAT = "%02x";
    private static final int SALT_LENGTH = 16;

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : digest) {
                hashedPassword.append(String.format(BYTE_FORMAT, b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing", e);
        }
    }
}
