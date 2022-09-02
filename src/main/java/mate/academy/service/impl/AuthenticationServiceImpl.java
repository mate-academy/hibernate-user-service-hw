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
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        User user = userByEmail.get();
        String hashPassword = HashUtil
                .hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Email or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || email.isBlank()) {
            throw new RegistrationException("Invalid credentials. "
                    + "Email can not be empty or blank");
        }
        if (password.isEmpty() || password.isBlank()) {
            throw new RegistrationException("Invalid credentials. "
                    + "Password can not be empty or blank");
        }
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("User with email " + email + " exists");
    }
}
