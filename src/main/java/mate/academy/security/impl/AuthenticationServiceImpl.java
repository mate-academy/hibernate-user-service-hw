package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            validateRegisterData(email, password);
            return userService.save(new User(email, password));
        }
        throw new RegistrationException("This email is already registered.");
    }

    @Override
    public User login(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !isValidPassword(user.get(), password)) {
            throw new AuthenticationException(
                    "Authentication failed for user with email: " + email);
        }
        return user.get();

    }

    private void validateRegisterData(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Login and password must not be null or empty");
        }
    }

    private boolean isValidPassword(User user, String password) {
        return password != null && user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
