package mate.academy.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import mate.academy.lib.Service;
import mate.academy.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final String HASH_ALGORITHM = "SHA-512";
    private static final int SALT_LENGTH = 16;

    @Override
    public String hashPassword(String password, String salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = messageDigest.digest((password + salt)
                    .getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hash) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't hash password", e);
        }
    }

    @Override
    public String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : salt) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
