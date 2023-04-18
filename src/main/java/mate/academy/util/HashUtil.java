package mate.academy.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte oneByte : digest) {
                hashedPassword.append(String.format("%02x", oneByte));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Couldn`t create hash", e);
        }
        return hashedPassword.toString();
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
