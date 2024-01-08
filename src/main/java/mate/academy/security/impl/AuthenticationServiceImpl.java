package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = null;
        try {
            user = userService.get(email);
        } catch (DataProcessingException e) {
            throw new AuthenticationException(e.getMessage(), e);
        }
        if (!user.getPassword().equals(HashUtil.hashPassword(password, user.getSalts()))) {
            throw new AuthenticationException("Incorrect password: " + password);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user;
        try {
            user = userService.get(email);
        } catch (DataProcessingException e) {
            user = new User(email);
            user.setSalts(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(password, user.getSalts()));
            return userService.add(user);
        }
        throw new RegistrationException("User with such email is already registered. "
                + "Email: " + email);
    }
}
