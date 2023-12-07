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
    public void register(String login, String password) throws RegistrationException {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("User with email " + login + " already exists");
        }
        userService.save(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Incorrect password");
    }
}
