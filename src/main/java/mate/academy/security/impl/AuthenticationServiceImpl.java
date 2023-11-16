package mate.academy.security.impl;

import java.util.Optional;
import java.util.function.Predicate;
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
        final Optional<User> optionalWithUser = userService.findByEmail(email);
        Predicate<String> isPasswordCorrect = s -> {
            User user = optionalWithUser.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            return user.getPassword().equals(hashedPassword);
        };
        if (optionalWithUser.isEmpty() || !isPasswordCorrect.test(password)) {
            throw new AuthenticationException("Can't authenticate user.");
        }
        return optionalWithUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Neither password nor email should be empty.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
