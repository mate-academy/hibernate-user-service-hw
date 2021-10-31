package mate.academy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.exception.RegistrationException;

public class HashUtil {
    private static final String HASHING_ALGORITHM = "SHA-512";
    private static final int SALT_SIZE = 16;

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String getHashPassword(String password, byte[] sale) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] digest = messageDigest.digest(sale);
            StringBuilder stringBuilder = new StringBuilder();
            for (byte temp : digest) {
                stringBuilder.append(String.format("%02x", temp));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RegistrationException("Can't registration, problem with Hashing. ", e);
        }
    }
}
