package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using " + CRYPTO_ALGORITHM
                + " algorithm");
        }
        return hashedPassword.toString();
    }

    public static byte[] getSalt(int size) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[size];
        secureRandom.nextBytes(salt);
        return salt;
    }

}
