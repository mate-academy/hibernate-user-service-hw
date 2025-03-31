package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String generateHash(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(salt);
            byte[] passBytes = messageDigest.digest(password.getBytes());
            for (byte passByte : passBytes) {
                hashedPassword.append(Integer.toString((passByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Can't generate hash using " + HASH_ALGORITHM
                    + " hashing algorithm", e);
        }
        return hashedPassword.toString();
    }
}
