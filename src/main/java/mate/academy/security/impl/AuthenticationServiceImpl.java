package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && userFromDB.get().getPassword()
                .equals(HashUtil.hashPassword(password,userFromDB.get().getSalt()))) {
            return userFromDB.get();
        } else {
            throw new AuthenticationException("Can't authenticate user");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Can't register user");
        } else {
            return userService.add(new User(email, password));
        }
    }
}
