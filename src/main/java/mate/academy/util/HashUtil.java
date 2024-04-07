package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    public static final int SALT_LENGTH_BYTES = 16;
    public static final String HEX_FORMAT = "%02x";
    private static final String HASH_ALGORITHM = "SHA-256";

    public HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(HEX_FORMAT, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(String.format(
                    "Couldn't create hash using %s algorithm!", HASH_ALGORITHM));
        }
        return hashedPassword.toString();
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}
