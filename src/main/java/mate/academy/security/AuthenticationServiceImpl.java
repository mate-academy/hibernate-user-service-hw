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
        if (userFromDbOptional.isEmpty()
                || !isPasswordValid(password, userFromDbOptional.get())) {
            throw new AuthenticationException(
                    "Something went wrong! Email or password is incorrect."
            );
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException(
                    "User with this email already exists: " + email
            );
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean isPasswordValid(String password, User user) {
        return user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
