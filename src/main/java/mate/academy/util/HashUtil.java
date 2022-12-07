package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";

    private HashUtil() {
    }

    public static String hashPassword(String password) {
        StringBuilder hashedPassword = new StringBuilder();
        String saltString = password.substring(0, 2);
        byte[] salt = saltString.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            byte[] digest = messageDigest.digest(password.getBytes());
            messageDigest.update(salt);
            for (byte b : digest) {
                hashedPassword.append(String.format("%2x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't create hash using "
                    + CRYPTO_ALGORITHM + " algorithm");
        }
        return hashedPassword.toString();

    }
}
