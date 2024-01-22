package mate.academy.security;

import mate.academy.exceptions.AuthenticationException;
import mate.academy.exceptions.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import java.util.Optional;


public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDBOptional = userService.findByEmail(email);
        if (userFromDBOptional.isEmpty()) {
            throw new AuthenticationException("Can`t authenticate user");
        }
        User user = userFromDBOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can`t authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDBOptional = userService.findByEmail(email);
        if (userFromDBOptional.isPresent()) {
            throw new RegistrationException("This email is already registered");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return user;
    }
}
