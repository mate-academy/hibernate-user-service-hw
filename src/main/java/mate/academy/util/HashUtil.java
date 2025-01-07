package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    public static byte[] getSalt() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt", e);
        }
    }

    public static String hashPassword(String plainPassword, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(plainPassword.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not found", e);
        }
        return hashedPassword.toString();
    }

    public static boolean verifyPassword(String plainPassword,
                                         byte[] salt,
                                         String storedHashedPassword) {
        String hashedPassword = hashPassword(plainPassword, salt);
        return hashedPassword.equals(storedHashedPassword);
    }
}
