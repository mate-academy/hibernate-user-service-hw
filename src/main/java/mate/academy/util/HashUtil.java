package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import mate.academy.exception.AuthenticationException;

public class HashUtil {

    private static final String HASH_ALGORITHM = "SHA-512";

    private HashUtil() {
        throw new UnsupportedOperationException("HashUtil class cannot be instantiated.");
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password.", e);
        }
    }

    public static void verifyPassword(String enteredPassword, byte[] storedPassword)
            throws AuthenticationException {
        byte[] hashedEnteredPassword = HashUtil.hashPassword(enteredPassword, storedPassword);
        if (!Arrays.equals(hashedEnteredPassword, storedPassword)) {
            throw new AuthenticationException("Authentication failed. Incorrect password.");
        }
    }
}
