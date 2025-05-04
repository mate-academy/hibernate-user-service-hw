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
    private static final Integer MIN_PASSWORD_LENGTH = 6;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.orElseThrow(() ->
                new AuthenticationException("No user with email: " + email));
        if (email == null || password == null
                || !HashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword())) {
            throw new AuthenticationException("Can't login with input data. Email: " + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email should be at least 4 symbols");
        }
        if (password == null || password.isEmpty() || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be at least 6 symbols");
        }
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register with input data. Email: " + email);
    }
}
