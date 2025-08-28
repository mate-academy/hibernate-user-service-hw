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
        Optional<User> maybeUser = userService.findByEmail(email);

        if (maybeUser.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }

        User user = maybeUser.get();

        if (!isPasswordValid(password, user)) {
            throw new AuthenticationException("Invalid email or password");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean isPasswordValid(String password, User user) {
        String expectedHash = user.getPassword();
        String actualHash = HashUtil.hash(password, user.getSalt());
        return expectedHash.equals(actualHash);
    }
}
