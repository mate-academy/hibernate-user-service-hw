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
        if (userFromDb.isPresent()
                && isValidPassword(userFromDb.get(), password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can't authenticate user by email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User by email: " + email + " is present");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        userService.add(newUser);
        return newUser;
    }

    private boolean isValidPassword(User userFromDb, String password) {
        return HashUtil.hashPassword(password, userFromDb.getSalt())
                .equals(userFromDb.getPassword());
    }
}
