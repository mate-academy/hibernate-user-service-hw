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
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException(
                "Your email is already registered. Email: " + email);
    }

    private boolean matchPasswords(String rawPassword, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
