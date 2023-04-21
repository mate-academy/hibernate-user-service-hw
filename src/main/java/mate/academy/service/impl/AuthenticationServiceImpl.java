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
        User user = userService.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("Email or password is incorrect!")
        );
        if (!user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Email or password is incorrect!");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Invalid email or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + "is exist");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
