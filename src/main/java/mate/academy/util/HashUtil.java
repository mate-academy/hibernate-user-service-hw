package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String ALGORITHM_SHA_512 = "SHA-512";
    
    private HashUtil() {
        
    }
    
    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_SHA_512);
            messageDigest.update(salt);
            
            byte[] bytes = messageDigest.digest(password.getBytes());
            
            for (byte b : bytes) {
                hashedPassword.append(String.format("%02x", b));
            }
            
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Exception occurred while " 
                    + "hashing password using SHA-512", e);
        }
        return hashedPassword.toString();
    }
    
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
