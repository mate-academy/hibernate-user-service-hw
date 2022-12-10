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
    private static final String VALID_EMAIL_PATTERN =
            "^[\\p{Alpha}\\d][\\p{Alpha}\\d.]+[\\p{Alpha}\\d]@[a-z]+\\.[a-z]{2,3}$";
    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!email.matches(VALID_EMAIL_PATTERN) || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Incorrect email or user with current email already exists");
        }
        return userService.add(new User(email, password));
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException(
                "Can't authenticate user. Email or password is incorrect");
    }
}
