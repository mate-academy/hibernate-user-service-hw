package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Incorrect email"));
        if (!HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword())) {
            throw new AuthenticationException("Incorrect password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " is already registered");
        }
        if (password.length() == 0) {
            throw new RegistrationException("Invalid password");
        }
        User user = new User();
        user.setEmail(email);
        byte[] userSalt = HashUtil.getSalt();
        user.setPassword(HashUtil.hashPassword(password, userSalt));
        return userService.add(user);
    }
}
