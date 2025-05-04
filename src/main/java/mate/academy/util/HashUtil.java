package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String HEX_FORMATTER_TWO_DIGITS = "%02x";
    private static final int LENGTH_SALT_ARR = 16;
    private static final SecureRandom secureRandom = new SecureRandom();

    public HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[LENGTH_SALT_ARR];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digits = messageDigest.digest(password.getBytes());
            for (byte bytes : digits) {
                hashedPassword.append(String.format(HEX_FORMATTER_TWO_DIGITS, bytes));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using SHA-515 algorithm", e);
        }
        return hashedPassword.toString();
    }
}
