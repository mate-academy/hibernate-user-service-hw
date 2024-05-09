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
    private static final int VALID_LENGTH = 6;

    @Inject
     private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException("Sorry, you don`t registered");
        }
        User user = byEmail.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Create custom error for this");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        checkValidData(email, password);
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.save(user);
    }

    private void checkValidData(String email, String password) {

        if (password == null || password.isEmpty()) {
            throw new RegistrationException("You forgot enter password, try again.");
        }
        if (password.length() < VALID_LENGTH) {
            throw new RegistrationException("I`ts a short password, minimal length "
                    + VALID_LENGTH);
        }
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("You forgot enter login, try again.");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
    }
}
