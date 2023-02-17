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
        Optional<User> userFromOptional = userService.findByEmail(email);
        User user = userFromOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userFromOptional.isEmpty() || !user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email, password);
        try {
            userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Can't register user");
        }
        return userService.findByEmail(email).orElseThrow(()
                -> new RegistrationException("Can't register user"));
    }
}
