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
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            User user = userFromDB.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't login: login or password is incorrect!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        checkEmail(email);
        checkPassword(password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email is already registered: " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private void checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("User should enter an email!");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("User should enter a password!");
        }
    }
}
