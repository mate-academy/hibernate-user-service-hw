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
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);
        if (userFromDb.isPresent()
                && isPasswordValid(userFromDb.get(), password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Invalid login or password. Please try again.");
    }

    @Override
    public User register(String login, String password, String repeatPassword)
            throws RegistrationException {
        if (isUserExist(login)) {
            throw new RegistrationException("User with this login already exist. "
                    + "Please try again.");
        }
        if (!password.equals(repeatPassword)) {
            throw new RegistrationException("Your passwords not equals. Please try again.");
        }
        return userService.add(new User(login, password));
    }

    private boolean isPasswordValid(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }

    private boolean isUserExist(String login) {
        Optional<User> userFromDb = userService.findByLogin(login);
        return userFromDb.isPresent();
    }
}
