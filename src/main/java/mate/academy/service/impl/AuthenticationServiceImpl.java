package mate.academy.service.impl;

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
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final int MIN_PASSWORD_LENGTH = 8;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Email or password are incorrect"));
        if (isCorrectPassword(user, password)) {
            return user;
        }
        throw new AuthenticationException("Email or password are incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!isEmailUnique(email)) {
            throw new RegistrationException("There is already user with such email");
        }
        if (!isEmailValid(email)) {
            throw new RegistrationException("Incorrect email");
        }
        if (!isPasswordValid(password)) {
            throw new RegistrationException("Password shorter than "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        userService.add(user);
        return user;
    }

    private boolean isEmailUnique(String email) {
        return userService.findByEmail(email).isEmpty();
    }

    private boolean isEmailValid(String email) {
        return email != null
                && email.matches(EMAIL_PATTERN);
    }

    private boolean isPasswordValid(String password) {
        return password != null
                && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isCorrectPassword(User user, String password) {
        return user.getPassword().equals(HashUtil
                .hashPassword(password, user.getSalt()));
    }
}
