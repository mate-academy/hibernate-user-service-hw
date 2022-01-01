package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty() || !byEmail.get().getPassword().equals(password)) {
            throw new AuthenticationException("Can't find User with email = " + email + " and password = " + password);
        }

        return byEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        return userService.add(new User(email, password));
    }
}
