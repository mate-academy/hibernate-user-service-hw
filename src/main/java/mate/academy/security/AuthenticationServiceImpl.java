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
    public void register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Account with email: " + email + " is already exist!");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password must contain any symbols!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User userFromDb = userFromDbOptional.get();
            if (userFromDb.getPassword()
                    .equals(HashUtil.hashPassword(password, userFromDb.getSalt()))) {
                return userFromDb;
            }
        }
        throw new AuthenticationException("Can't authenticate user!");
    }
}
