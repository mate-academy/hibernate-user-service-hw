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
        throw new AuthenticationException("Can not authenticate the user");
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("Failed to register");
    }
}
