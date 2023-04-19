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
        User user = userFromDbOptional.get();
        if (userFromDbOptional.isEmpty() || !HashUtil.hashPassword(password, user.getSalt())
                .equals(user.getPassword())) {
            throw new AuthenticationException("Can't authenticate user. Wrong email or password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("A user with this email " + email + " already exists");
        }
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Password or email is incorrect");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
