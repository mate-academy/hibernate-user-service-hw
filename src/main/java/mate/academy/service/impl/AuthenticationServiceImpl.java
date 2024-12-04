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
        User user = new User();

        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isEmpty()) {
            user.setEmail(email);
            user.setPassword(password);
            return user;
        } else {
            throw new AuthenticationException("User with email " + email + " already exist");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        try {
            User userLogin = login(email, password);
            return userService.add(userLogin);
        } catch (Exception e) {
            throw new RegistrationException("User can't register ", e);
        }
    }
}
