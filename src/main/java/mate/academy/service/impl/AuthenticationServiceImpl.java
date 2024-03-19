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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("User not found");
        }
        User user = userOptional.get();
        String hashedEnteredPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedEnteredPassword)) {
            throw new AuthenticationException("Incorrect password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password must not be empty");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        userService.add(user);

        return user;
    }
}
