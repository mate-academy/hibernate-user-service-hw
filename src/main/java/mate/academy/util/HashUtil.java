package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.exception.AuthenticationException;

public class HashUtil {
    private static final String ALGORITHM_SHA = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[]{17};
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) throws AuthenticationException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_SHA);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte d : digest) {
                stringBuilder.append(String.format("%02x", d));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using SHA-512 algorithm ", e);
        }
        return String.valueOf(stringBuilder);
    }
}
