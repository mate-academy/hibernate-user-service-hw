package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    public static final int PASSWORD_LENGTH = 8;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && userFromDb.get()
                            .getPassword()
                            .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can't authenticate user by email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.length() < PASSWORD_LENGTH) {
            throw new RegistrationException("You should use password longer than 8 symbols");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new DataProcessingException("User with such email already exists" + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
