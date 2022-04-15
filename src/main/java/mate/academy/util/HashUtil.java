package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {

    private static final String CRYPTO_ALGORITHM = "SHA-512";

    public HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedFunction = new StringBuilder();
        try {
            MessageDigest instance = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            instance.update(salt);
            byte[] digest = instance.digest(password.getBytes());
            for (byte b : digest) {
                hashedFunction.append(String.format("%02x", b));
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can`t hash password with algorithm: "
                    + CRYPTO_ALGORITHM, e);
        }
        return hashedFunction.toString();
    }
}
