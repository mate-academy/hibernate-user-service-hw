package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtilSha512 {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtilSha512() {

    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte symbol : digest) {
                hashedPassword.append(String.format("%02x", symbol));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Could not create hash using " + CRYPTO_ALGORITHM + " algorithm", e);
        }
        return hashedPassword.toString();
    }
}
