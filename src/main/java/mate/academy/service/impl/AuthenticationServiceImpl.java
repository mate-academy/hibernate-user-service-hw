package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isEmpty() || password.isEmpty() || email == null) {
            throw new AuthenticationException("Don't valid password or email");
        }
        return userByEmailOptional.get();
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isPresent() || password.isEmpty()) {
            throw new RegistrationException("User cant register user with email: " + email);
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);

        return userService.add(newUser);
    }
}
