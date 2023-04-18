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
        Optional<User> optionalUserFromDb = userService.findByEmail(email);
        if (optionalUserFromDb.isEmpty()) {
            throw new AuthenticationException("Can`t authentication user with email: " + email);
        }
        User userFromDb = optionalUserFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        if (userFromDb.getPassword().equals(hashedPassword)) {
            return userFromDb;
        }
        throw new AuthenticationException("Can`t authentication user with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.length() < 6) {
            throw new RegistrationException("Incorrect email or password");
        }
        return userService.add(new User(email, password));
    }
}
