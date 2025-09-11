package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.exception.DataProcessingException;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
        throw new UnsupportedOperationException();
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        if (password.isEmpty()) {
            throw new DataProcessingException("Can't create hash for empty password string");
        }
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create password using SHA-512 algorithm. ",e);
        }
        return hashedPassword.toString();
    }

}
