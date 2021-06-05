package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {

    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] encryptedPassword = messageDigest.digest(password.getBytes());
            for (byte currentByte : encryptedPassword) {
                hashedPassword.append(String.format("%02x", currentByte));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Couldn't create hash using SHA-512 algorithm", e);
        }
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }
}
