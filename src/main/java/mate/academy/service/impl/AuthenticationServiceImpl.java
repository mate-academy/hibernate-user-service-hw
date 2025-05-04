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
        User userByEmail = userService.findByEmail(email).get();
        if (userByEmail != null
                && userByEmail.getPassword().equals(
                        HashUtil.hashPassword(password, userByEmail.getSalt()))) {
            return userByEmail;
        } else {
            throw new AuthenticationException("Login failed: "
                    + "Invalid email or password.");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("Registration failed: "
                    + "Email is already in use.");
        }
        if (password == null || password.isBlank()) {
            throw new RegistrationException("Registration failed: "
                    + "Password cannot be null or empty.");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
