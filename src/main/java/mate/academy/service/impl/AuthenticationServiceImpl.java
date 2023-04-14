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
        if (isNullOrEmpty(email, password)) {
            throw new AuthenticationException("Email or password shouldn't be null or empty");
        }

        if (authenticate(email, password)) {
            throw new AuthenticationException("Email or password is invalid");
        }
        return userService.findByEmail(email).get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email shouldn't be empty or null");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password shouldn't be empty or null");
        }
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with such an email already exists");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean isNullOrEmpty(String email, String password) {
        return email == null || password == null || email.isEmpty()
                || password.isEmpty();
    }

    private boolean authenticate(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        return user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()));
    }
}
