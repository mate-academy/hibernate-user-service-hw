package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could`t create hash using SHA-512 algorithm", e);
        }
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
