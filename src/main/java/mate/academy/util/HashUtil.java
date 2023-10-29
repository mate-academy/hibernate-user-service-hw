package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final int SALT_BYTE_CAPACITY = 16;
    private static final String FORMAT_SPECIFIER = "%02x";

    public static String hashPassword(String originPassword, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = originPassword.getBytes();
            for (byte b : digest) {
                hashedPassword.append(String.format(FORMAT_SPECIFIER, b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Wrong hash algorithm chosen", e);
        }
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_CAPACITY];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
