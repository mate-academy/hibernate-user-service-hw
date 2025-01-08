package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            } else {
                throw new AuthenticationException("Invalid password.");
            }
        } else {
            throw new AuthenticationException("User not found.");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("Email already exist.");
        }

        User newUser = new User(email, password);
        return userService.add(newUser);
    }
}

