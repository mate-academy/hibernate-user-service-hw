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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        String passwordFromUser =
                HashUtil.hashPassword(password, userFromDbOptional.get().getSalt());
        String passwordFromDb = userFromDbOptional.get().getPassword();
        if (userFromDbOptional.isEmpty() || !passwordFromUser.equals(passwordFromDb)) {
            throw new AuthenticationException("Login data is not valid");
        } else {
            return userFromDbOptional.get();
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty() && !password.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Registration data are not valid");
    }
}
