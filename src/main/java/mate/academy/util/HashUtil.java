package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    public static byte [] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte [] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte [] salt) {
        StringBuilder hashPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte [] digits = messageDigest.digest(password.getBytes());
            for (byte digit : digits) {
                hashPassword.append(String.format("%02x", digit));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash using SHA-512 algorithm", e);
        }
        return hashPassword.toString();
    }
}
