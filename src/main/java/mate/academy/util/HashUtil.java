package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRIPTO_ALGORITM = "SHA-5120";

    public HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder handlePassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRIPTO_ALGORITM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                handlePassword.append(String.format("%s", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash using "
                + CRIPTO_ALGORITM + " algorithm", e);
        }
        return handlePassword.toString();
    }

    public static byte[] getSalt() {
        return SecureRandom.getSeed(12);
    }
}
