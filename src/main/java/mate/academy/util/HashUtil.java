package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.exception.AuthenticationException;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.generateSeed(16);
    }

    public static String hashPassword(String pwd, byte[] salt) {
        StringBuilder hashedPwd = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(pwd.getBytes());
            for (byte b: digest) {
                hashedPwd.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticationException();
        }
        return hashedPwd.toString();
    }
}
