package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashUtil {
    private static final int SALT_LENGTH = 16;
    private static final String CRYPTO_ALGORITHM = "SHA-512";
    private static final String HASH_FORMAT = "%02x";

    public static byte[] getSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte b : digest) {
                hashedPassword.append(String.format(HASH_FORMAT, b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    String.format("Couldn't create hash using '%s' algorithm",
                            CRYPTO_ALGORITHM), e);
        }
    }
}
