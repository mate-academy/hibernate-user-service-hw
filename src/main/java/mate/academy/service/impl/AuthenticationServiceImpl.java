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
    public User login(String email, String password)
            throws AuthenticationException {
        User user = userService.findByLogin(email).orElseThrow(
                () -> new AuthenticationException("Email not found")
        );
        if (!user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Invalid password");
        }
        return user;
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        Optional<User> user = userService.findByLogin(email);
        if (user.isPresent()) {
            throw new RegistrationException("This email is already taken, " + email);
        }
        if (email == null || email.isEmpty()
                || password == null || password.isEmpty()) {
            throw new RegistrationException("Email or password shouldn't be null or empty");
        }
        return userService.add(new User(email, password));
    }
}
