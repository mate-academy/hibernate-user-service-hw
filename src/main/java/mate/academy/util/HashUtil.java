package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(
            String password,
            byte[] salt
    ) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedBytes =
                    md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : hashedBytes) {
                sb.append(
                        String.format("%02x", b)
                );
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Can't hash password",
                    e
            );
        }
    }
}
