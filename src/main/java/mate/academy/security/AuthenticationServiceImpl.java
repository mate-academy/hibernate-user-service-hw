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
        if (userFromDbOptional.isEmpty() || !passwordCheck(userFromDbOptional, password)) {
            throw new AuthenticationException("Can't authenticate user with email: " + email);
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email: "
                    + email + ". User with this email already exist.");
        }
        return userService.add(new User(email, password));
    }

    private boolean passwordCheck(Optional<User> userOptional, String password) {
        User user = userOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashPassword);
    }
}
