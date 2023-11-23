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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.orElseThrow();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException(
                    "User is already registered with such email: " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Can't register user");
        }
        return userService.add(user);
    }
}
