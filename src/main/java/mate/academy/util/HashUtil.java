package mate.academy.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO = "SHA-512";

    private HashUtil() {
    }

    public static byte [] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte [] salt) {
        StringBuilder hashPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO);
            messageDigest.update(salt);
            for (byte b : messageDigest.digest(password.getBytes())) {
                hashPassword.append(String.format("%02x", b));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't create hash using SHA512", e);
        }
        return hashPassword.toString();
    }
}
