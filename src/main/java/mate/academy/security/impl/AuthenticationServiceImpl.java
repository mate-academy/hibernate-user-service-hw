package mate.academy.security.impl;

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
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return userService.findByEmail(email)
                .filter(user -> user.getPassword()
                        .equals(HashUtil.hashPassword(password, user.getSalt())))
                .orElseThrow(() -> new AuthenticationException("Incorrect email or password"));
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
