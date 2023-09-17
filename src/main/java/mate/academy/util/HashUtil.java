package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASH_ALGORITHM = "SHA-512";

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] digest = md.digest(password.getBytes());
            StringBuilder passwordBuilder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                passwordBuilder.append(Integer.toHexString(0xFF & digest[i]));
            }
            return passwordBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't get hash password ", e);
        }
    }

}
