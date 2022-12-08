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
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MAX_PASSWORD_SIZE = 128;
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9_!#$%&’*+=?`{|}~^.-]+@[a-zA-Z0-9.-]+";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !isEqualHashPassword(userFromDb.get(), password)) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email already register");
        }
        if (!isEmailValid(email)) {
            throw new RegistrationException("Email is not valid");
        }
        if (!isPasswordValid(password)) {
            throw new RegistrationException("Password is not valid");
        }
        return userService.add(new User(email, password));
    }

    private boolean isEmailValid(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD_SIZE
                && password.length() <= MAX_PASSWORD_SIZE;
    }

    private boolean isEqualHashPassword(User user, String password) {
        return user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
