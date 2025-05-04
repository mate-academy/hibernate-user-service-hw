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
    public User login(String email, String password) {
        User userFromDB = getUserByEmail(email);

        if (isPasswordValid(password, userFromDB)) {
            return userFromDB;
        }
        throw new AuthenticationException("Can't authenticate user login or password is wrong");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userByEmailOptional = userService.findByEmail(email);

        if (userByEmailOptional.isPresent()) {
            throw new RegistrationException("User with email " + email + " is already present."
                    + "Please enter other details");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    private boolean isPasswordValid(String password, User userFromDB) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        return userFromDB.getPassword().equals(hashedPassword);
    }

    private User getUserByEmail(String email) {
        Optional<User> userByEmailOptional = userService.findByEmail(email);
        if (userByEmailOptional.isEmpty()) {
            throw new AuthenticationException("Can't find user by email: " + email);
        }
        return userByEmailOptional.get();
    }
}
