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
    @Inject
    private UserDao userDao;
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(login);
        if (userFromDbOptional.isEmpty() || !(userFromDbOptional.get().getPassword().equals(
                (HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))))) {
            throw new AuthenticationException("Incorrect login or password: "
                    + login + ":" + password);
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (password.isEmpty() || userDao.checkLoginExists(login)) {
            throw new RegistrationException("Login already exists or password is empty!");
        }
        User user = new User();
        user.setPassword(password);
        user.setLogin(login);
        userService.add(user);
        return user;
    }

}
