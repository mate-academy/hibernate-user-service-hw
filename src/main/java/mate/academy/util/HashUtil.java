package mate.academy.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashUtil {
    private static final int SALT_SIZE = 16;
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String CRYPTO_ALGORITHM = "PBKDF2WithHmacSHA1";

    private HashUtil() {

    }

    public static String hashPassword(String password) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        secureRandom.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        byte[] hash;
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(CRYPTO_ALGORITHM);
            hash = secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Can't generate hash using PBKDF2WithHmacSHA1 algorithm", e);
        }
        return new String(hash);
    }
}
