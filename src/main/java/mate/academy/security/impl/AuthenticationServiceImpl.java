package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException("Invalid email");
        }
        User user = byEmail.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Invalid password or email");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty()){
            throw new RegistrationException("During registration, check that the email is unique (i.e., not already used)");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
