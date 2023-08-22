package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);

        if (byEmail.isEmpty() || !passwordValidation(byEmail.get(), password)) {
            throw new AuthenticationException("Could not authenticate user by email:" + email);
        }

        return byEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> byEmail = userService.findByEmail(email);

        if (byEmail.isPresent()) {
            throw new RegistrationException(
                    "This email address is already registered. Email: " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }

    private boolean passwordValidation(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
