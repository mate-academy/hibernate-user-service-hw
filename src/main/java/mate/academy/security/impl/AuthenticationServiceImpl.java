package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataValidationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User register(String email, String password) throws RegistrationException {
        isValidEmail(email);
        isValidPassword(password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Can't authenticate user: " +
                        "Wrong email " + email));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate user: " +
                    "Wrong password " + password);
        }
        return user;
    }

    private boolean isValidEmail(String email) {
        if (email == null
                || !email.contains("@")
                || !email.contains(".")) {
            throw new DataValidationException("Email isn't valid");
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password == null
                || password.isEmpty()
                || password.isBlank()) {
            throw new DataValidationException("Password cannot be empty");
        }
        return true;
    }
}
