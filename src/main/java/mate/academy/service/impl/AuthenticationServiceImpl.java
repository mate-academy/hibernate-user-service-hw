package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int MIN_PASSWORD_LENGTH = 1;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        passwordValidation(password);
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user: invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        passwordValidation(password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " is already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    void passwordValidation(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException(
                    "Password length must be equal to or greater than " + MIN_PASSWORD_LENGTH);
        }
    }
}
