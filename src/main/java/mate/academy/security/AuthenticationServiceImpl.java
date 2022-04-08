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
        if (userFromDB.isEmpty()) {
            throw new AuthenticationException("There is no user with this email " + email);
        }
        User user = userFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Incorrect password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = null;
        try {
            user = new User();
            user.setEmail(email);
            user.setSalt(HashUtil.getSalt());
            user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Can't register a new user with email" + email);
        }
    }
}
