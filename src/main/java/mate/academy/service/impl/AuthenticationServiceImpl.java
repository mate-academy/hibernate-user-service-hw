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

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_LOGIN_LENGTH = 8;
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByLogin(login)
                .orElseThrow(() ->
                        new AuthenticationException("Password or login is incorrect"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Password or login is incorrect");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (login == null || password == null
                || login.length() < MIN_LOGIN_LENGTH
                || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password or login is incorrect");
        }
        if (userService.findByLogin(login).isPresent()) {
            throw new RegistrationException("Such user already exists");
        }
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        User newUser = new User(login, hashedPassword);
        newUser.setSalt(salt);
        return userService.add(newUser);
    }
}
