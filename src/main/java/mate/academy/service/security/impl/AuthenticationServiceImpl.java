package mate.academy.service.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.service.security.AuthenticationService;
import mate.academy.util.PasswordUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        User user = userOptional.orElse(null);
        if (userOptional.isPresent() && isValidPassword(password, user)) {
            return user;
        }
        throw new AuthenticationException("Incorrect email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            User user = new User(email, password);
            return userService.add(user);
        }
        throw new RegistrationException("User with such email is already registered!");
    }

    private boolean isValidPassword(String password, User user) {
        return PasswordUtil.getHash(password, user.getSalt())
                .equals(user.getPassword());
    }
}
