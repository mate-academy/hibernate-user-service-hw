package mate.academy.service.impl;

import java.util.Optional;
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

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-_]+@[\\w-_]+$");
    private static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (EMAIL_PATTERN.matcher(email).matches() && !password.isEmpty()) {
            Optional<User> userFromDBOptional = userService.findByEmail(email);
            if (userFromDBOptional.isPresent()
                    && userFromDBOptional.get().getPassword()
                    .equals(HashUtil.hashPassword(password, userFromDBOptional.get().getSalt()))) {
                return userFromDBOptional.get();
            }
        }
        throw new AuthenticationException("Wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new RegistrationException("Wrong email format");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password is empty");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new RegistrationException("Use strong password!");
        }
        try {
            return userService.add(new User(email, password));
        } catch (Exception e) {
            throw new RegistrationException("Error occurred when adding the user");
        }
    }
}
