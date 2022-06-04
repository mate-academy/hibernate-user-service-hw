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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user. Wrong email or password.");
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user. Wrong email or password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User newUser = new User();
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("User with email: " + email + " already exists.");
        }
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
