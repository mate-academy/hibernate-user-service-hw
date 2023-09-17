package mate.academy.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITM = "SHA-256";
    private static final int SIZE = 32;
    private static SecureRandom secureRandom = new SecureRandom();

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Couldn't create hash using " + CRYPTO_ALGORITM
                    + " algoritm", e);
        }
        return hashedPassword.toString();
    }
}
