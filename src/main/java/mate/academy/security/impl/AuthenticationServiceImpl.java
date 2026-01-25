package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;

    @Override
    public User login(String email, String password) {
        if (email == null
                || password == null
                || email.isBlank()
                || password.isBlank()) {
            throw new AuthenticationException("Invalid email or password");
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(
                        "Invalid email or password")
                );

        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException(
                    "Invalid email or password"
            );
        }
        return user;
    }

    @Override
    public User register(String email, String password) {
        if (email == null
                || password == null
                || email.isBlank()
                || password.isBlank()) {
            throw new RegistrationException(
                    "Email or password must be provided"
            );
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Email already exists"
            );
        }

        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));

        return userService.add(user);
    }
}
