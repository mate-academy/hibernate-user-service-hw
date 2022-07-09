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
    private static final int MINIMUM_PASSWORD_LENGTH = 8;
    @Inject
    private UserService userService;
    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Something wrong with login or password");
        }
        User user = userOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Something wrong with login or password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists at DB");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password can't be less than 8 symbols");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
