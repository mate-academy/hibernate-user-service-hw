package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HashUtil {
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final int ARRAY_LENGTH = 16;
    private static final String PATTERN = "%02x";

    private HashUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[ARRAY_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            return IntStream.range(0, digest.length)
                    .mapToObj(i -> String.format(PATTERN, digest[i]))
                    .collect(Collectors.joining());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    String.format("Could not create hash using %s algorithm", CRYPTO_ALGORITHM), e);
        }
    }
}
