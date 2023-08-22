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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty() || !checkPassword(userOptional.get(), password)) {
            throw new AuthenticationException(String.format(
                    "Can't authenticate user with email: " + email + ", password: " + password));
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException(String.format(
                    "Can't register a user with empty email or password, email: " + email
                            + ", password: " + password));
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with same email already exists: " + email);
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
