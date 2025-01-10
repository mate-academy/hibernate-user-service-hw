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
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can't find user with email: " + email);
        }
        User user = userFromDbOptional.get();
        String passwordOfUserFromDB = user.getPassword();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (passwordOfUserFromDB.equals(hashPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Can't authenticate user with email: " + email);
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User createdUser = new User(email, password);
        User user = userService.add(createdUser);
        if (user != null) {
            return user;
        } else {
            throw new RegistrationException("Can't register user with email: " + email);
        }
    }
}
