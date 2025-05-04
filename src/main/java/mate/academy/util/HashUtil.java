package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte hashedByte : digest) {
                stringBuilder.append(String.format("%02x", hashedByte));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Failed to create hash with algorithm = ["
                    + ALGORITHM + "]", e);
        }
        return stringBuilder.toString();
    }
}
