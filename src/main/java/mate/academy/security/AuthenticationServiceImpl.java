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
    private static final int MIN_LENGTH = 8;
    private static final String EMAIL_VALIDATOR =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            return user.get();
        }
        throw new AuthenticationException ("Email or password was incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.matches(EMAIL_VALIDATOR) && password.length() >= MIN_LENGTH) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Invalid email or password");
    }
}
