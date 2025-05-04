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
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDb.get();
        String hashedPass = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPass)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        if (userService.findByEmail(email).isEmpty()) {
            user.setLogin(email);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("User with such email already registered");
    }
}
