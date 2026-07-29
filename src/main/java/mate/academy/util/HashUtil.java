package mate.academy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private HashUtil() {
    }

    public static String hashPassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-512");
            md.update(salt);
            byte[] hashedBytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find SHA-512 algorithm", e);
        }
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
