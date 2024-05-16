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
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Can`t authentication email: " + email);
        }
        User userFromDB = optionalUser.get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        if (userFromDB.getPassword().equals(hashedPassword)) {
            return userFromDB;
        }
        throw new AuthenticationException("Can`t authentication user: " + userFromDB);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        User registeredUser = userService.add(user);
        if (registeredUser == null) {
            throw new RegistrationException("Failed to register user: " + registeredUser);
        }
        return registeredUser;
    }
}
