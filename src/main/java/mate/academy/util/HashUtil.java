package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    public static byte[] generateSalt() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] array = new byte[16];
            secureRandom.nextBytes(array);
            return array;
        } catch (Exception e) {
            throw new RuntimeException("Can not generate salt : ",e);
        }
    }

    public static String hashPassword(String s, byte[] salt) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(s.getBytes());
            for (byte b : digest) {
                stringBuilder.append(String.format("%02x",b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not create hash using SHA-512 algorithm",e);
        }
        return stringBuilder.toString();

    }
}
