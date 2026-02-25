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
        return userService.findByEmail(email).filter(user -> {
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            return user.getPassword().equals(hashedPassword);
        })
        .orElseThrow(
                () -> new AuthenticationException("Authentication failed, invalid credentials."));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);

        if (userFromDB.isPresent()) {
            throw new RegistrationException("User with email: " + email + " is already exists.");
        }

        return userService.add(new User(email, password));
    }
}
