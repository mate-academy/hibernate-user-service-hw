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
                () -> new AuthenticationException("Can't find this email : "
                        + email + ", try another one!"));
        checkPassword(password, user);
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Email or password are invalid!");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Can't register user, email has already been used: " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private void checkPassword(String password, User user) throws AuthenticationException {
        String hashedPassword = HashUtil.hashPassword(
                password, user.getSalt());
        if (!hashedPassword.equals(password)) {
            throw new AuthenticationException("Password is incorrect, try again!");
        }
    }
}
