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
        Optional<User> byEmailOptional = userService.findByEmail(email);
        if (checkForNullEmptyWhitespace(email) && checkForNullEmptyWhitespace(password)
                && byEmailOptional.isPresent()) {
            User user = byEmailOptional.get();
            String hashedPass = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPass)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't login user. Wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!checkForNullEmptyWhitespace(email) || !checkForNullEmptyWhitespace(password)
                || userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user. "
                    + "Please try another email and password");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userService.add(user);
        return user;
    }

    private boolean checkForNullEmptyWhitespace(String string) {
        return string != null && !string.isEmpty()
                && string.trim().length() == string.length();
    }

}
