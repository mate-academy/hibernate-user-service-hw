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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);

        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can't find user with email: " + email);
        }
        User userFromDb = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        if (userFromDb.getPassword().equals(hashedPassword)) {
            return userFromDb;
        } else {
            throw new AuthenticationException("Email or Password is incorrect.");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            throw new RegistrationException("Email or Password is invalid.");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + " is already existed.");
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RegistrationException("Email format is invalid.");
        }

        if (password.length() < 8) {
            throw new RegistrationException("Password must be at least 8 characters long.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.add(newUser);
        return newUser;
    }
}
