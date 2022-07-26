package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int PASSWORD_MIN_LENGTH = 8;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userEmail = userService.findByEmail(email);
        User user = userEmail.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userService.findByEmail(email).isPresent()
                && user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user. Login or password incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()
                && password.length() >= PASSWORD_MIN_LENGTH) {
            User user = new User();
            user.setPassword(password);
            user.setLogin(email);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register new user. Password is incorrect");
    }
}
