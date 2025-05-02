package mate.academy.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> exists = userService.findByEmail(email);
        if (exists.isPresent()) {
            throw new RegistrationException("Email already registered: " + email);
        }
        try {
            byte[] salt = generateSalt();
            String saltHex = bytesToHex(salt);
            String hash = hashPassword(password, salt);
            User user = new User();
            user.setEmail(email);
            user.setSalt(saltHex);
            user.setPasswordHash(hash);
            return userService.add(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RegistrationException("Error generating password hash", e);
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }
        User user = userOpt.get();
        try {
            // Use the overload that takes the hex string directly:
            String expectedHash = hashPassword(password, user.getSalt());
            if (!expectedHash.equals(user.getPasswordHash())) {
                throw new AuthenticationException("Invalid email or password");
            }
            return user;
        } catch (NoSuchAlgorithmException e) {
            throw new AuthenticationException("Error verifying password", e);
        }
    }

    private byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return salt;
    }

    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
        md.update(salt);
        byte[] hashed = md.digest(password.getBytes());
        return bytesToHex(hashed);
    }

    private String hashPassword(String password, String saltHex) throws NoSuchAlgorithmException {
        return hashPassword(password, hexToBytes(saltHex));
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
