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
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank");
        }
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty()
                || !userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Incorrect email or password: " + email);
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("Can not register User with email" + email);
    }
}
