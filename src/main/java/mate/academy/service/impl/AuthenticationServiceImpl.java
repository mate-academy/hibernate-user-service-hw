package mate.academy.service.impl;

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
        if (email.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Email and password cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new AuthenticationException("Invalid email address. "
                    + "Email address must contain \"@\"");
        }
        User userFromDb = userService.findByEmail(email).orElse(null);
        if (userFromDb == null) {
            throw new AuthenticationException("User with such email: " + email
                    + " not found in database. Try another email to login.");
        }
        if (!userFromDb.getPassword()
                .equals(HashUtil.generateHash(password, userFromDb.getSalt()))) {
            throw new AuthenticationException("Invalid password for user: "
                    + userFromDb.getEmail());
        }
        return userFromDb;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password cannot be empty.");
        }
        if (!email.contains("@")) {
            throw new RegistrationException("Invalid email address. Email address must contain @");
        }
        User userFromDb = userService.findByEmail(email).orElse(null);
        if (userFromDb != null) {
            throw new RegistrationException("User with such email: " + email
                    + " already exists. Try another email to register.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
