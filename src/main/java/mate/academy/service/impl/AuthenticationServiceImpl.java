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
        User userFromDB = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("User with email " + email + " is not registered."));

        if (!HashUtil.hashPassword(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword())) {
            throw new AuthenticationException("User password is incorrect.");
        }

        return userFromDB;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password should not be empty.");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " is already registered.");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.add(user);
    }
}
