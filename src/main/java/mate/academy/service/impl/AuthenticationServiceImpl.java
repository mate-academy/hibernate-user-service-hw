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
    private static final int MIN_PASSWORD_LENGTH = 10;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException("Wrong login or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email \"" + email + "\" already exists");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters long!");
        }
        return userService.add(new User(email, password));
    }
}
