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
        if (email == null || email.trim().isEmpty()) {
            throw new AuthenticationException("Email is null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Password is null or empty");
        }
        String clearEmail = email.trim().toLowerCase();
        Optional<User> optionalUser = userService.findByEmail(clearEmail);
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword()
                .equals(HashUtil.hashPassword(password, optionalUser.get().getSalt()))) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.trim().isEmpty()) {
            throw new RegistrationException("Email is null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Password is null or empty");
        }
        String clearEmail = email.trim().toLowerCase();
        if (userService.findByEmail(clearEmail).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }
        User newUser = new User();
        newUser.setEmail(clearEmail);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
