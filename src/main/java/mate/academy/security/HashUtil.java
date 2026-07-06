package mate.academy.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);

            byte[] hash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder builder = new StringBuilder();

            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't hash password", e);
        }
    }
}
