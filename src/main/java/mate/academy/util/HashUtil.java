package mate.academy.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {
    public static final int BYTE_ARRAY_SIZE = 16;
    private static final String CRYPTO_ALGORITHMS = "SHA-512";
    private static final String CAN_T_HASH_PASSWORD_MSG = "Can't hashPassword ";

    private HashUtil() {
    }

    public static byte[] salt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[BYTE_ARRAY_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder stringBuilder = new StringBuilder();
        String generatedPassword;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(CRYPTO_ALGORITHMS);
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            for (byte passwordBytes : bytes) {
                stringBuilder
                        .append(Integer.toString((passwordBytes & 0xff) + 0x100, 16)
                                .substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (Exception e) {
            throw new RuntimeException(CAN_T_HASH_PASSWORD_MSG + e.getMessage(), e);
        }
        return generatedPassword;
    }
}
