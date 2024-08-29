package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User register(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.save(user);
    }

    @Override
    public User login(String login, String password) {
        Optional<User> userByLogin = userService.findByLogin(login);
        if (userByLogin.isPresent()) {
            User user = userByLogin.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Cant find login or user password incorrect!");

    }

    public User authenticate(String login, String password) {
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Login and password must not be null or empty");
        }
        if (userService.findByLogin(login).isEmpty()) {
            return register(login, password);
        } else {
            return login(login, password);
        }
    }

}
