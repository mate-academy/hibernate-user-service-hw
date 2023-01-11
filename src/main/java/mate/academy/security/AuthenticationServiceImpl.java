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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Login or password was incorrect.");
        }
        User user = userOptional.get();
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return userOptional.get();
        }
        throw new AuthenticationException("Login or password was incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isEmpty()) {
            throw new RegistrationException("A user with this login already exists.");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password should not be empty.");
        }
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
