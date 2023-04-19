package mate.academy.service.impl;

import java.util.NoSuchElementException;
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
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByEmail(login).isPresent()) {
            throw new RegistrationException("Can't register, login is already used");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDb = userService.findByEmail(login).orElseThrow(() ->
                new NoSuchElementException("Can't find user by email: " + login + " from DB"));
        if (!userFromDb.getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.getSalt()))) {
            throw new AuthenticationException("Can't authenticate user with login: "
                    + login + " and password: + " + password);
        }
        return userFromDb;
    }
}
