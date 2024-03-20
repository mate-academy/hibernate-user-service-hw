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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            User existingUser = userFromDb.get();
            String hashedPassword = HashUtil.hashPassword(password, existingUser.getSalt());
            if (hashedPassword.equals(existingUser.getPassword())) {
                return existingUser;
            }
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email "
                    + email + " has already existed");
        }
        User newUser = getUser(email, password);
        return userService.add(newUser);
    }

    private User getUser(String email, String password) {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return newUser;
    }
}
