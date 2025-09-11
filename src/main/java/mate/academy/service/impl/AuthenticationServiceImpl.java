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
        Optional<User> userFromDB = userService.findByEmail(email);
        boolean isAuthenticated = userFromDB.isPresent()
                && userFromDB.get().getPassword().equals(
                        HashUtil.hashPassword(password, userFromDB.get().getSalt())
                );
        if (!isAuthenticated) {
            throw new AuthenticationException("Can't authenticate user with email: " + email);
        }
        return userFromDB.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty()) {
            throw new RegistrationException("User email cannot be empty!");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("User password cannot be empty!");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + "already registered!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
