package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {

    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digests = messageDigest.digest(password.getBytes());
            for (byte digest : digests) {
                hashPassword.append(String.format("%02x", digest));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create hash using SHA-512 algorithm", e);
        }
        return hashPassword.toString();
    }
}
