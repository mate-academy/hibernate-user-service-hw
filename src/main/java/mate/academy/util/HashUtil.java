package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    public static final String CRYPTO_ALGORITHM = "Sha-512";

    public HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] bytesFromPassword = messageDigest.digest(password.getBytes());
            for (byte bytes : bytesFromPassword) {
                hashedPassword.append(String.format("%02x", bytes));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return hashedPassword.toString();
    }
}
