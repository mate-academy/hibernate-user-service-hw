package mate.academy.service.impl;

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
        AuthenticationException exception = new AuthenticationException(
                "Incorrect email or password");
        User user = userService.findByEmail(email).orElseThrow(() -> exception);
        if (user.getPassword().isEmpty() || !user.getPassword()
                        .equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw exception;
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Invalid registration data");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
