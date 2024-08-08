package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cannot generate salt", e);
        }
    }

    public static String getHashOfPassword(String password, byte[] salt) {
        StringBuilder hashPassword = new StringBuilder();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(salt);

            byte[] digest = messageDigest.digest(password.getBytes());

            for (byte b : digest) {
                hashPassword.append(String.format("%02x", b));
            }

            return hashPassword.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't get hash of password with algorithm: "
                    + ALGORITHM, e);
        }
    }
}
