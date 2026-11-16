package mate.academy.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtil {
    private HashUtil() {
    }

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt)
            throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        ByteBuffer buffer = ByteBuffer.allocate(passwordBytes.length + saltBytes.length);
        buffer.put(passwordBytes);
        buffer.put(saltBytes);
        byte[] combinedBytes = buffer.array();
        byte[] hashedBytes = messageDigest.digest(combinedBytes);
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
