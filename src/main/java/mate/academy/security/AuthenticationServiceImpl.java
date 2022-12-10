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
        if (userFromDb.isPresent() && matchPasswords(password, userFromDb.get())) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can not authenticate user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() || !email.matches("^(.+)@(.+)$")) {
            throw new RegistrationException("Can not register user with email " + email);
        }
        if (password.isBlank()) {
            throw new RegistrationException("Password should contain symbols");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean matchPasswords(String password, User userFromDb) {
        String hashPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashPassword.equals(userFromDb.getPassword());
    }
}
