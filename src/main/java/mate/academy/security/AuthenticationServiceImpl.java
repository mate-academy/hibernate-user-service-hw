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
        if (login.isEmpty() || password.isEmpty() || userService.findByEmail(login).isPresent()) {
            throw new RegistrationException("Please, use another email");
        }
        User user = new User();
        user.setPassword(password);
        user.setLogin(login);
        return userService.add(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(login);
        if (passwordIsValid(userFromDbOptional, password)) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    private boolean passwordIsValid(Optional<User> foundUser, String password) {
        return foundUser.get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, foundUser.get().getSalt()));
    }
}
