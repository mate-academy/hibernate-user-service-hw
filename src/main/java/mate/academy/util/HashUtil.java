package mate.academy.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {

    private static final String CRYPTO_ALGORITHM = "SHA-256";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hash(String login, byte[] salt) {
        StringBuilder hashedResult = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(login.getBytes());

            for (byte b : digest) {
                hashedResult.append(String.format("%02x", b));
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not hash login" + login, e);
        }
        return hashedResult.toString();
    }
}
