package mate.academy.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't hash password with " + CRYPTO_ALGORITHM, e);
        }
        messageDigest.update(salt);
        return new BigInteger(messageDigest.digest(password.getBytes())).toString(16);
    }
}
