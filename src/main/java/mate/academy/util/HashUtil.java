package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String FORMAT_IDENTIFICATION = "%02x";
    private static final int SALT_ARRAY_SIZE = 16;

    private HashUtil() {
    }

    public static byte[] getSalt() {
        Random secureRandom = new Random();
        byte[] salt = new byte[SALT_ARRAY_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b: digest) {
                hashedPassword.append(String.format(FORMAT_IDENTIFICATION, b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not create hash using "
                    + CRYPTO_ALGORITHM + " algorithm",e);
        }
        return hashedPassword.toString();
    }
}
