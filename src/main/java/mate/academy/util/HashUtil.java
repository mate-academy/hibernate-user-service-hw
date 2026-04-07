package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {

    }

    public static String createSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        StringBuilder hashedPassword = new StringBuilder();

        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);

            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(saltBytes);
            byte[] digest = messageDigest.digest(password.getBytes());

            for (byte b : digest) {
                hashedPassword.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Could not create hash using " + CRYPTO_ALGORITHM + " algorithm", e);
        }
        return hashedPassword.toString();
    }
}
