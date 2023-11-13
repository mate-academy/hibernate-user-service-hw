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
    public void register(String login, String password) throws RegistrationException {
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Can't register this user");
        }
        User user = new User(login, password);
        userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);
        if (userFromDb.isEmpty()
                || !HashUtil.hashPassword(password, userFromDb.get().getSalt())
                .equals(userFromDb.get().getPassword())) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userFromDb.get();
    }
}
