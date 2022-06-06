package mate.academy.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtil {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION = 65536;
    private static final int KEY_LENGTH = 128;

    public static byte[] generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[32];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();

        SecretKeyFactory factory;
        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATION,
                KEY_LENGTH);

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Incorrect algorithm", ex);
        }
        try {
            byte[] hash = factory.generateSecret(spec).getEncoded();
            for (byte element : hash) {
                hashedPassword.append(String.format("%02x", element));
            }
            return hashedPassword.toString();
        } catch (InvalidKeySpecException ex) {
            throw new RuntimeException("Invalid Key Spec", ex);
        }
    }
}
