package mate.academy.service.impl;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Email and password can not be empty.");
        }
        User user = userService.findByEmail(email)
                .orElseThrow(AuthenticationException.findByIdSupplier(email, User.class));

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthenticationException("Passwords do not match.");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already in use.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(hash);

        return userService.add(user);
    }
}
