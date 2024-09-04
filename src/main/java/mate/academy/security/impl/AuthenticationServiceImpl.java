package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can`t login user by email " + email);
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can`t login user " + user);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null) {
            throw new RegistrationException("Can`t register a new user by email " + email);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
