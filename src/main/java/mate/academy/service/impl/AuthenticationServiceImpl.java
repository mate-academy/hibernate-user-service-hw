package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Dao
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user.getUsername() == null) {
            throw new AuthenticationException("Invalid email");
        }
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Invalid password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = userService.findByEmail(email);
        if (user.getUsername() != null) {
            if (user.getUsername().equals(email)) {
                throw new RegistrationException("User already exists");
            }
        }
        user.setPassword(password);
        user.setUsername(email);
        userService.add(user);
        return user;
    }
}
