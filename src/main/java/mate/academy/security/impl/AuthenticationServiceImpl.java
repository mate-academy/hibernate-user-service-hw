package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Cannot authenticate user");
        }
        User user = userFromDbOptional.get();
        String encryptedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(encryptedPassword)) {
            return user;
        }
        throw new AuthenticationException("Cannot authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent() || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Cannot register user");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
