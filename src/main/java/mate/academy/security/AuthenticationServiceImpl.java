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
        User user = null;
        String hashedPassword = null;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        }
        if (userOptional.isEmpty() || !user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate User by email: " + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()
                || email.isEmpty() || password.length() < 4) {
            throw new RegistrationException("Can't register user. Incorrect email or password");
        }
        return userService.add(new User(email, password));
    }
}
