package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static final String ALGORITHM = "SHA-256";
    public static final String ERROR_DURING_CREATION_OF_MESSAGE_DIGEST_INSTANCE =
            "Error during creation of MessageDigest instance";
    public static final String ENCODING = "%02x";
    public static final byte[] DEFAULT_SALT = new byte[]{19};

    private HashUtil() {
    }

    public static String hashPassword(String initialPassword, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(salt);
            byte[] digestedPassword = messageDigest.digest(initialPassword.getBytes());
            for (Byte b : digestedPassword) {
                hashedPassword.append(String.format(ENCODING, b));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(ERROR_DURING_CREATION_OF_MESSAGE_DIGEST_INSTANCE, e);
        }
    }
}
