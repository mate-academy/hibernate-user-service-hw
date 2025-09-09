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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can not login! Incorrect email or password");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByEmail(login).isEmpty()) {
            User user = new User();
            user.setEmail(login);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("Can not register! This email is already registered.");
    }

    private boolean matchPasswords(String rawPassword, User userFromDb) {
        String hashedPassword =
                HashUtil.getHashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
