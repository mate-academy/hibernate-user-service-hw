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
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isEmpty()) {
            throw new AuthenticationException("User with email " + email + " not found");
        }
        User user = existingUser.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashPassword)) {
            throw new AuthenticationException("Incorrect password for user with email " + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("User already exist with email: " + email);
        }
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        User newUser = new User(email, hashedPassword, salt);
        return userService.add(newUser);
    }
}
