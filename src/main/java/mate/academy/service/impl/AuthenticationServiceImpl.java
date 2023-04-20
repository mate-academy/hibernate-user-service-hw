package mate.academy.service.impl;

import java.util.Objects;
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
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user with email: " + email);
        }
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Can't authenticate user with email: " + email));
        String hashedPassword = HashUtil.getHashedPassword(password, user.getSalt());
        if (Objects.equals(user.getPassword(), hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("email " + email + " or password is invalid");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + "already exists");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
