package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGO = "SHA-512";
    private static final String STRING_FORMAT = "%02x";

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGO);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(STRING_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can`t hash password using "
                    + HASH_ALGO + " Algorythm");
        }
        return hashedPassword.toString();
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
