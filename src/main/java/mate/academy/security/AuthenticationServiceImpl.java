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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException("Password must not be empty");
        }
        User user;
        if (userService.findByEmail(email).isEmpty()) {
            user = new User();
            user.setPassword(password);
            user.setEmail(email);
            userService.add(user);
        } else {
            throw new RegistrationException("This email is already occupied");
        }
        return user;
    }
}
