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
    public static final int MIN_PASSWORD_LENGTH = 6;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && isValidPassword(userFromDB.get(), password)) {
            throw new AuthenticationException("Invalid login or password!");
        }
        return userFromDB.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            throw new RegistrationException("User with this login already exists!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length cannot be less than 6 symbols!");
        }
        return userService.add(new User(email, password));
    }

    private boolean isValidPassword(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
