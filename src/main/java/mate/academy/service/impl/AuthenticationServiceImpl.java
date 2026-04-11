package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.SecurityService;
import mate.academy.service.UserService;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Inject
    private SecurityService securityService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Incorrect email or password");
        }

        User user = userOptional.get();
        String hashedPassword = securityService.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }

        String salt = securityService.generateSalt();
        String hashedPassword = securityService.hashPassword(password, salt);

        User user = new User();
        user.setEmail(email);
        user.setSalt(salt);
        user.setPassword(hashedPassword);

        return userService.add(user);
    }
}