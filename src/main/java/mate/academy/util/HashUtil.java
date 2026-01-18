package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {

    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder str = new StringBuilder();
        try {
            MessageDigest digestMessage = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            digestMessage.update(salt);
            byte[] digest = digestMessage.digest(password.getBytes());
            for (byte b : digest) {
                str.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return str.toString();
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
