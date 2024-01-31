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
        if (userService.findByEmail(email).isPresent()) {
            User user = userService.findByEmail(email).get();
            if (HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword())) {
                return user;
            }
        }

        throw new AuthenticationException("Either email or password is wrong. Try again.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email is already in use");
        }

        if (password.isEmpty()) {
            throw new RegistrationException("Password field is empty");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
