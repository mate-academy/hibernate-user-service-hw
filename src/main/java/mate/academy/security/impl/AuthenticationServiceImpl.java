package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        User user = optionalUserByEmail.orElse(null);
        if (optionalUserByEmail.isPresent()
                && user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return user;
        } else {
            throw new AuthenticationException("Can authenticate user. Wrong email or password.");
        }

    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);

        try {
            return userService.add(user);
        } catch (DataProcessingException e) {
            throw new RegistrationException("Can not register user with email: " + email
                    + ". Try again with another one.");
        }
    }
}
