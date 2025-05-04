package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final int SALT_LENGTH = 16;
    private static final String HASHING_ALGORITHM = "SHA-256";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom secureRandom = new SecureRandom(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte digestByte : digest) {
                hashedPassword.append(String.format("%02x", digestByte));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't create hash with " + HASHING_ALGORITHM, e);
        }
        return hashedPassword.toString();
    }
}
