package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final String REGEX_PATTERN = "^(.+)@(\\w+)$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        String hashedPassword = HashUtils.hashPassword(password, user.get().getSalt());
        if (user.isPresent() && password.equals(hashedPassword)) {
            return user.get();
        } else {
            throw new AuthenticationException("Cannot authenticate user by email: " + email);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateUser(email, password);
        User user = new User(email,password);
        return userService.add(user);
    }

    public boolean validateUser(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("Unable to register with current email: " + email);
        }
        if (!isEmailValid(email)) {
            throw new RegistrationException("Not valid email format " + email);
        }
        if (!isPasswordValid(password)) {
            throw new RegistrationException(
                    "Password length isnt correct.Min length of password: " + PASSWORD_MIN_LENGTH);
        }
        return true;
    }

    public boolean isEmailValid(String email) {
        return email.matches(REGEX_PATTERN);
    }

    public boolean isPasswordValid(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH;
    }
}
