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
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Inject
    private UserService userService;

    public AuthenticationServiceImpl() {
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email, password);
        try {
            validationUser(user);
            return userService.add(user);
        } catch (Exception e) {
            throw new RegistrationException("Email "
                    + email
                    + " or password "
                    + password
                    + " isn't valid!", e);
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.orElseThrow(
                () -> new AuthenticationException("Can't authenticate user"));

        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return user;
    }

    private void validationUser(User user) {
        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new RuntimeException("Email and password cannot be empty");
        }
        if (!user.getEmail().matches(REGEX_EMAIL)) {
            throw new RuntimeException("Invalid email format");
        }
    }
}
