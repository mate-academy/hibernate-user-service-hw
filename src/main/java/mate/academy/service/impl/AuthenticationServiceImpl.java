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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (!userFromDbOptional.isEmpty()) {
            User user = userFromDbOptional.get();
            String hashedSaltedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedSaltedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || password.isBlank() || !userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("Can't register user.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
