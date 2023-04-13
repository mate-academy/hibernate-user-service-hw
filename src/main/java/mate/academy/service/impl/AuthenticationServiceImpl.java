package mate.academy.service.impl;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private static final String EMAIL_PATTERN_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private final Pattern patternForEmail = Pattern.compile(EMAIL_PATTERN_REGEX);
    private final Pattern patternForPassword = Pattern.compile(PASSWORD_PATTERN_REGEX);
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        final Optional<User> userFromDb = userService.findByEmail(email);
        isEmptyEmailOrInvalidPassword(userFromDb, email, password);
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        if (isValidEmail(email)
                && isValidPassword(password)) {
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register user with these email: "
                + email + " or password");
    }

    private boolean isEmptyEmailOrInvalidPassword(Optional<User> user,
                                                  String email, String password)
            throws AuthenticationException {
        if (user.isEmpty()
                || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user by user email: "
                    + email + " or password");
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = patternForEmail.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        Matcher matcher = patternForPassword.matcher(password);
        return matcher.matches();
    }
}
