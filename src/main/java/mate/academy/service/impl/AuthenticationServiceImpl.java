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
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password must not be empty");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // Хэш будет сделан в UserService.add
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }

        User user = userOptional.get();
        String hashedInput = HashUtil.hashPassword(password, user.getSalt());

        if (!user.getPassword().equals(hashedInput)) {
            throw new AuthenticationException("Invalid email or password");
        }

        return user;
    }
}
