package mate.academy.service.impl;

import javax.naming.AuthenticationException;
import mate.academy.RegistrationException;
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
        User user = userService.findByEmail(email).orElse(null);
        if (user == null || !user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Inputs are invalid");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Incorrect input params!"
                    + " Email or password can't be empty!");
        }
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with email: "
                    + email + " is already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
