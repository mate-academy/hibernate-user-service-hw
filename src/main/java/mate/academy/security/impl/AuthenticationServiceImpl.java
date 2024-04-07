package mate.academy.security.impl;

import java.util.Objects;
import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && isValidPasswordForUser(userFromDb.get(), password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException(String.format("User with email: %s  already exists",
                    email));
        }
        if (password == null || password.isBlank() || password.length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean isValidPasswordForUser(User user, String password) {
        return Objects.equals(user.getPassword(), HashUtil.hashPassword(password, user.getSalt()));
    }
}
