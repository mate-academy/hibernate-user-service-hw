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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email == null || password == null) {
            throw new AuthenticationException("Incorrect email or password");
        }
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Incorrect email or password");
        }
        User userFromDB = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        if (userFromDB.getPassword().equals(hashedPassword)) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isBlank()) {
            throw new RegistrationException("Email can't be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        if (password == null || password.isBlank()) {
            throw new RegistrationException("Password can't be empty");
        }
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Can't register user with email: " + email, e);
        }
    }
}
