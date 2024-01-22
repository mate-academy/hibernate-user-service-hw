package mate.academy.security.impl;

import java.sql.SQLException;
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
        Optional<User> getUserOptionalFromDB = userService.findByEmail(email);
        User user = getUserOptionalFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userService.findByEmail(email).isPresent() && user.getPassword()
                .equals(hashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Can't login user with email \"" + email
                    + "\". Check login and password!", new SQLException());
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Registration failed! User with email \""
                    + email + "\" is already exists in DB.",
                    new SQLException());
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
