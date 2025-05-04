package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User register(String login, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isPresent() || password.isEmpty()) {
            throw new RegistrationException("Can`t register user with login: "
                    + login + "User with this login already exists or password is invalid");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can`t authenticate user with login: " + login);
    }
}
