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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("User with email " + email + " not found");
        }
        User user = userFromDbOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());

        if (user.getPassword().equals(hashPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Invalid password for email " + email);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || !email.contains("@")) {
            throw new RegistrationException("Invalid email format: " + email);
        }
        if (password == null || password.length() < 10) {
            throw new RegistrationException("Password must be at least 10 characters long");
        }

        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new RegistrationException("Email " + email + " is already registered");
        }

        User newUser = new User();
        byte[] salt = HashUtil.getSalt();
        newUser.setSalt(salt);
        newUser.setPassword(HashUtil.hashPassword(password, salt));
        newUser.setEmail(email);

        return userService.save(newUser);
    }
}
