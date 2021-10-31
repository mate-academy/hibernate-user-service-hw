package mate.academy.service.impl;

import java.util.Optional;
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
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("The current user exists with email " + email);
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent()) {
            User user = userByEmailOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authentication user with email " + email);
    }
}
