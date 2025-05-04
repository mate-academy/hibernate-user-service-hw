package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    @Override
    public User login(String email, String password) {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can`t authenticate user");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashedPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can`t authenticate user");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userEmail = userService.findByEmail(email);
        if (userEmail.isEmpty()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);

        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashedPassword(password, salt);

        user.setSalt(salt);
        user.setPassword(hashedPassword);

        return userService.add(user);
    }
}
