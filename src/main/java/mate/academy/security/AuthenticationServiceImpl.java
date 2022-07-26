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
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && HashUtil.hashPassword(password, user.get().getSalt())
                .equals(user.get().getPassword())) {
            return user.get();
        }
        throw new AuthenticationException("Login or password was incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("User with same email already exist.Email: " + email
                    + "Change email or try to login");
        }
        if (password.length() < 10) {
            throw new RegistrationException("Password must contain 10 or more symbols");
        }
        User current = new User();
        current.setEmail(email);
        current.setPassword(password);
        return userService.add(current);
    }
}
