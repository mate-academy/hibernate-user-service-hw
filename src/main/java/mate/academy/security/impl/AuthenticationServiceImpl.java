package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Inject
    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Incorrect email or password");
        }
        User user = userOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already in use");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
