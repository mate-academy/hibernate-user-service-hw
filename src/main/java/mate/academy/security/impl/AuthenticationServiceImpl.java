package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
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
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can not login User with login: " + login);
        }
        User user = userFromDb.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Can not login user with login" + login);
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        if (user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
            throw new RegistrationException("Can not register user with empty login or password");
        }
        return userService.add(user);

    }
}
