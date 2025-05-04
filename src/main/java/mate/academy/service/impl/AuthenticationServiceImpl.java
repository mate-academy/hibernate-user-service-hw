package mate.academy.service.impl;

import java.util.Optional;
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
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && userFromDB.get().getPassword().equals(
                HashUtil.hash(password, userFromDB.get().getSalt())
        )) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateEmail(email);
        validatePassword(password);
        validateUserExistence(email);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private void validateEmail(String email) throws RegistrationException {
        if (!email.matches(EMAIL_REGEX)) {
            throw new RegistrationException("Invalid email format " + email);
        }
    }

    private void validatePassword(String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }
    }

    private void validateUserExistence(String email) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format("Email %s taken", email));
        }
    }
}
