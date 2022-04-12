package mate.academy.security;

import java.util.Optional;
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
        if (userService.findByLogin(login).isPresent()) {
            throw new RegistrationException("Login " + login + " already in use");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String login, String password) {
        Optional<User> userFromDB = userService.findByLogin(login);
        if (userFromDB.isPresent()) {
            User user = userFromDB.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new ArithmeticException("Wrong login or password");
    }
}
