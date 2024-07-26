package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    UserService userService;
    @Override
    public void register(String login, String password) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        userService.save(newUser);
    }

    @Override
    public User login(String login, String password) {
        Optional<User> userFromDBOptional = userService.findByLogin(login);
        if (userFromDBOptional.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDBOptional.get();
        String hashedPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }
}
