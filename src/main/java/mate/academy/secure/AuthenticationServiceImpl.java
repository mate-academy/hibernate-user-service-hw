package mate.academy.secure;

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
        Optional<User> userFromDb = userService.getByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDb.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.getByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("User with this email already exist");
        }
        User user = new User();
        user.setSalt(HashUtil.getSalt());
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        user.setPassword(hashedPassword);
        user.setEmail(email);
        userService.add(user);
        return user;
    }
}
