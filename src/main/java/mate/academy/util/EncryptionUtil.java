package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private EncryptionUtil() {
    }

    public static byte[] generateSalt(int numberOfBytes) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[numberOfBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    public static String encrypt(String str, byte[] salt) {
        StringBuilder hashedString = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(str.getBytes());
            for (byte b : digest) {
                hashedString.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Wrong algorithm " + CRYPTO_ALGORITHM, e);
        }
        return hashedString.toString();
    }
}
