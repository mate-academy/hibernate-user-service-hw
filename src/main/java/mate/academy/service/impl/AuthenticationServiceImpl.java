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
            throw new AuthenticationException("User with email " + email + " is not registered. ");
        }
        User userFromDb = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        if (hashedPassword.equals(userFromDb.getPassword())) {
            return userFromDb;
        }
        throw new AuthenticationException("Incorrect password for user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("User with email " + email + " is already registered");
        }
        User newUser = new User(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
