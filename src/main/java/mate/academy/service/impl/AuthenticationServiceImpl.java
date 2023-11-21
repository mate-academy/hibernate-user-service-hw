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
    private static final String AUTH_ERROR_MSG = "Current user doesn't exist or password is wrong";
    private static final String CREDENTIALS_ERR_MSG =
            "Can't register user with current credentials.";
    private static final String ALREADY_REGISTERED_USER_ERR_MSG =
            "User with given email already exist Email: ";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()
                && validatePasswords(password, userFromDbOptional.get())) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException(AUTH_ERROR_MSG);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if ((email == null || email.isEmpty()) || (password == null || password.isEmpty())) {
            throw new RegistrationException(CREDENTIALS_ERR_MSG);
        }
        Optional<User> userFromDBoptional = userService.findByEmail(email);
        if (userFromDBoptional.isPresent()) {
            throw new RegistrationException(ALREADY_REGISTERED_USER_ERR_MSG + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean validatePasswords(String password, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
