package mate.academy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int SALT_LENGTH = 16;

    public HashUtil() {
    }

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            messageDigest.update(saltBytes);
            byte[] digest = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create "
                    + "hash using SHA-512 algorithm!: " + CRYPTO_ALGORITHM, e);
        }
    }

    public static boolean isPasswordValid(String inputPassword,
                                          String storedSalt, String storedHash) {
        String hashedInput = hashPassword(inputPassword, storedSalt);
        return hashedInput.equals(storedHash);
    }
}
