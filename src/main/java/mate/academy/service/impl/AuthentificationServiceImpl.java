package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthentificationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {
    private static final int PASSWORD_MIN_LENGTH = 5;
    @Inject
    private UserDao userDao;
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(login);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("No user with such login: "
                    + login);
        }
        User user = userFromDbOptional.get();
        if (!(user.getPassword().equals(
                (HashUtil.hashPassword(password, user.getSalt()))))) {
            throw new AuthenticationException("Incorrect password for login:" + login);
        }
        return user;
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        isLoginExists(login);
        if (password == null || password.length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Ineligible password. "
                    + "Length must be more than 5. " + password);
        }
        User user = new User();
        user.setPassword(password);
        user.setLogin(login);
        userService.add(user);
        return user;
    }

    private void isLoginExists(String login) {
        if (userDao.checkLoginExists(login)) {
            throw new RegistrationException("Login " + login + " already exists."
                    + "\nPlease come up with another login!");
        }
    }
}
