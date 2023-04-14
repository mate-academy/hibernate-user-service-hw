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
        Optional<User> user = userService.findByEmail(email);
        if (email == null || password == null || email.isEmpty()
                || password.isEmpty() || user.isEmpty()
                || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Email or password is invalid");
        }
        return user.get();
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
}
