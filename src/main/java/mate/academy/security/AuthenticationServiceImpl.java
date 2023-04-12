package mate.academy.security;

import java.util.Optional;
import java.util.regex.Matcher;
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
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        final Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()
                || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user by user email: "
                    + email + " or password");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        if (userService.findByEmail(email).isEmpty()
                && isValidEmail(email)
                && isValidPassword(password)) {
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register user with these email: "
                + email + " or password");
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
