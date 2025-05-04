package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    public static final String HASH_ALGORITHM = "SHA-512";

    private HashUtil() {

    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String pass, byte[] salt) {
        StringBuilder hashedPass = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(pass.getBytes());
            for (byte b: digest) {
                hashedPass.append(String.format("%02x", b));
            }
            return hashedPass.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't create hash sing " + HASH_ALGORITHM
                    + "algoritm", e);
        }
    }
}
