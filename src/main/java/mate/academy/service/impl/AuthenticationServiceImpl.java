package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        if (email.equals(userService.findByEmail(email).get().getEmail())) {
            if (password.equals(userService.findByEmail(email).get().getPassword())) {
                return userService.findByEmail(email).get();
            }
        }
        throw new AuthenticationException("This email or password is uncorrected");
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new AuthenticationException("This email - " + email + " exists in the database");
    }
}
