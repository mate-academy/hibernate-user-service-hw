package mate.academy.security;

import java.security.SecureRandom;

public class SaltUtil {
    private static final int SALT_LENGTH = 16;

    private SaltUtil() {
    }

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
}
