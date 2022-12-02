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
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String REGEX_PATTERN = "^(.+)@(\\w+)$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && matchPasswords(email, userFromDB.get())) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Can't authenticate user by email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateUser(email, password);
        return userService.add(new User(email, password));
    }

    private void validateUser(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("Can't register user with email " + email);
        }
        if (!isValidEmail(email)) {
            throw new RegistrationException("Not valid email format " + email);
        }
        if (!isValidPassword(password)) {
            throw new RegistrationException("Password length must be min "
                    + PASSWORD_MIN_LENGTH + " symbols");
        }
    }

    private boolean matchPasswords(String password, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }

    private boolean isValidEmail(String email) {
        return email.matches(REGEX_PATTERN);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH;
    }
}
