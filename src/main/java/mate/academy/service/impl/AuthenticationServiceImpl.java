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
    public static final String LOGIN_OR_PASSWORD_IS_WRONG = "Login or password is wrong.";
    public static final String USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_OR_THE_PASSWORD_IS_EMPTY =
            "User with such email already exists or the password is empty.";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, HashUtil.DEFAULT_SALT))) {
            throw new AuthenticationException(LOGIN_OR_PASSWORD_IS_WRONG);
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.isEmpty()) {
            throw new RegistrationException(
                    USER_WITH_SUCH_EMAIL_ALREADY_EXISTS_OR_THE_PASSWORD_IS_EMPTY);
        }
        return userService.add(new User(email, password));
    }
}
