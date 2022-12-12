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
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Enter both email and password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with such email is already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            User user = userFromDB.get();
            String inputHashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(inputHashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Incorrect email or password");
    }
}
