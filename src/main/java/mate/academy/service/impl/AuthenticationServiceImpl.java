package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.PasswordHasher;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email.isEmpty()) {
            throw new AuthenticationException("Email must not be empty. "
                    + "Email = " + email);
        }

        if (password.isEmpty()) {
            throw new AuthenticationException("Password must not be empty.");
        }

        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(PasswordHasher.hashPassword(
                    password, user.getSalt().getBytes()))) {
                return user;
            }
        }

        throw new AuthenticationException("Password or Email is incorrect. Email = " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> existedUser = userService.findByEmail(email);
        if (existedUser.isPresent()) {
            throw new RegistrationException("User with email: " + email + " already exist");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
