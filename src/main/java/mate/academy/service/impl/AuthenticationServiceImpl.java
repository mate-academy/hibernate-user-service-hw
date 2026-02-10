package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Email and password can not be empty.");
        }
        User user = userService.findByEmail(email)
                .orElseThrow(AuthenticationException.findByIdSupplier(email, User.class));

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new AuthenticationException("Passwords do not match.");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already in use.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }
}
