package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationException;
import mate.academy.security.AuthenticationService;
import mate.academy.security.RegistrationException;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        try {
            Optional<User> userOptional = userService.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
                if (user.getPassword().equals(hashedPassword)) {
                    return user;
                }
            }
            throw new AuthenticationException("Invalid email or password");
        } catch (Exception e) {
            throw new AuthenticationException("Authentication failed", e);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty");
        }
        User user = userService.add(new User(email, password));
        if (user != null) {
            return user;
        }
        throw new RegistrationException("Register failed");
    }
}
