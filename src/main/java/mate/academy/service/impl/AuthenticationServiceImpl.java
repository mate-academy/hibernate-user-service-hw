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
    public User login(String login, String password) {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isEmpty()
                || !passwordCheck(optionalUser.get(), password)) {
            throw new AuthenticationException("Wrong login or password");
        }
        return optionalUser.get();
    }

    @Override
    public User register(String login, String password) {
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("User already exists");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.save(user);
    }

    private boolean passwordCheck(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
