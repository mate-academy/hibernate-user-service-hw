package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final String FORMAT = "%02x";
    private static final int SIZE = 16;
    
    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(FORMAT, b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash with: "
                    + HASH_ALGORITHM, e);
        }
    }
}
