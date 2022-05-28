package mate.academy.security;

import java.util.Optional;
import java.util.regex.Pattern;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String REGEX_EMAIL =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*"
                    + "@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty() || !userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Login or password was incorrect...");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        if (!userService.findByEmail(email).isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || !pattern.matcher(email).matches()
        ) {
            throw new RegistrationException("Can't register user with the entered parameters");
        }
        return userService.add(createUserByEmailAndLogin(email, password));
    }

    private User createUserByEmailAndLogin(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return user;
    }
}
