package mate.security.impl;

import java.util.Optional;
import mate.exception.AuthenticationException;
import mate.exception.RegistrationException;
import mate.lib.Inject;
import mate.lib.Service;
import mate.model.User;
import mate.security.AuthenticationService;
import mate.service.UserService;
import mate.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty() || !checkUserPassword(password,userFromDbOptional)) {
            throw new AuthenticationException("Can't authenticate user by email or password");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty() && !password.isEmpty()) {
            User user = new User(email, password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register new User"
                + " because your email is already exists in DB or your password is empty");
    }

    private boolean checkUserPassword(String password, Optional<User> user) {
        return HashUtil.hashPassword(password, user.get().getSalt())
                .equals(user.get().getPassword());
    }
}
