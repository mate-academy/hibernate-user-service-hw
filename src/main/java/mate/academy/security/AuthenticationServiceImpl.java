package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (checkUserEmail(email).isPresent()) {
            User user = checkUserEmail(email).get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (checkUserEmail(email).isPresent()) {
            throw new RegistrationException("This email has already registered." + email);
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("This password is incorrect. "
                    + "Must be longer than: " + MIN_PASSWORD_LENGTH + " symbols");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private Optional<User> checkUserEmail(String email) {
        return userService.findByEmail(email);
    }
}
