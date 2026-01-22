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
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.findByEmail(email).isEmpty()
                ? userService.add(user)
                : throwRegistrationException(email);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);

        if (userFromDb.isEmpty() || !isValid(password, userFromDb.get())) {
            throw new AuthenticationException("Invalid email or password");
        }
        return userFromDb.get();
    }

    private boolean isValid(String password, User user) {
        return HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword());
    }

    private User throwRegistrationException(String email) throws RegistrationException {
        throw new RegistrationException("User with email " + email + " already exists");
    }
}
