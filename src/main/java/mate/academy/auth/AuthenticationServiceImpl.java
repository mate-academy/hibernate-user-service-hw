package mate.academy.auth;

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
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashedInputPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedInputPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("User is not registered or wrong password provided");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("Can't register user with such email " + email);
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
