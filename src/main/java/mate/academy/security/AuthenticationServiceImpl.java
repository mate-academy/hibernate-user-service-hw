package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationFailureException;
import mate.academy.exception.RegistrationFailureException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationFailureException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationFailureException("Authentication failed. "
                        + "Please check your email and password and try again. "
                        + "If you don't have an account, you can register a new user."));

        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationFailureException("Authentication failed. "
                    + "Please check your email and password and try again. "
                    + "If you don't have an account, you can register a new user.");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationFailureException {
        Optional<User> userFromDboptional = userService.findByEmail(email);
        if (userFromDboptional.isEmpty()) {
            throw new RegistrationFailureException("Wops! Smth went wrong, "
                   + "can't register the user");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userService.save(user);
        return user;
    }
}
