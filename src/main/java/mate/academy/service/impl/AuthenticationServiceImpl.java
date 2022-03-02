package mate.academy.service.impl;

import java.util.regex.Pattern;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String ERROR_AUTH_MES = "Incorrect email or password";
    private static final int MIN_LEN_PASSWORD = 4;
    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ERROR_AUTH_MES));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException(ERROR_AUTH_MES);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.length() < MIN_LEN_PASSWORD) {
            throw new RegistrationException("Short password");
        } else if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new RegistrationException("Invalid email");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
