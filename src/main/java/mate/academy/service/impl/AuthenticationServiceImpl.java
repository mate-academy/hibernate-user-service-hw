package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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

    public AuthenticationServiceImpl() {
    }

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userByLogin = userService.findByLogin(login);
        if (userByLogin.isEmpty()) {
            throw new AuthenticationException("Can not authenticate user");
        }
        User user = userByLogin.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Can not authenticate user");
        }
    }

    @Override
    public User register(String login, String password) {

        User user = new User(login, password);

        return userService.save(user);
    }
}
