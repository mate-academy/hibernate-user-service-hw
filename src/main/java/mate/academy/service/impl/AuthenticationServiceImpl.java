package mate.academy.service.impl;

import java.util.Optional;
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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent() && HashUtil.hashPassword(password,
                userFromDbOptional.get()
                        .getSalt())
                .equals(userFromDbOptional.get()
                        .getPassword())) {
            return userFromDbOptional.get();
        }

        throw new AuthenticationException("Email or password is invalid! For user with email "
                + email);
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("User with such email: " + email
                + " has already been inserted to the DB");
    }
}
