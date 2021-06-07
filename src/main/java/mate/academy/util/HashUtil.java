package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String ENCRYPTING_ALGORITHM = "SHA-256";
    private static final int SALT_ARRAY_SIZE = 16;

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_ARRAY_SIZE];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTING_ALGORITHM);
            messageDigest.update(salt);
            for (byte value : messageDigest.digest(password.getBytes())) {
                hashedPassword.append(String.format("%02x", value));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash using "
                    + ENCRYPTING_ALGORITHM + " algorithm", e);
        }
        return hashedPassword.toString();
    }
}
