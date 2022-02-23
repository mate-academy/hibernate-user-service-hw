package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-256";

    private HashUtil() {

    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());

            for (byte saltByte : digest) {
                hashedPassword.append(String.format("%02x", saltByte));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hashed password with "
                    + CRYPTO_ALGORITHM + " algorithm.");
        }
        return hashedPassword.toString();
    }
}
