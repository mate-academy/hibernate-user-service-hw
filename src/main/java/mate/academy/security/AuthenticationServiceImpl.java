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
    private static final String EMAIL_TEMPLATE = "\\w+@\\w+\\.[a-z]+";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User currentUser = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, currentUser.getSalt());
            if (currentUser.getPassword().equals(hashedPassword)) {
                return currentUser;
            }
        }
        throw new AuthenticationException("Can't authenticate User with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.matches(EMAIL_TEMPLATE) && userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("Can't register user with this email: " + email);
    }
}
