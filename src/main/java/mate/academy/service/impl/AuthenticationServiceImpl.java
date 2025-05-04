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
        Optional<User> userFormDb = userService.findByEmail(email);
        if (userFormDb.isPresent()) {
            User user = userFormDb.get();
            if (HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Authentication error: wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Registration error: email already exists "
                    + "or empty password");
        }
        return userService.add(new User(email, password));
    }
}
