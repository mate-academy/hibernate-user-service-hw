package mate.academy.security.impl;

import java.util.Optional;
import java.util.regex.Pattern;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int PASSWORD_LENGTH = 6;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (!validEmail(email)) {
            throw new RegistrationException("Email " + email + " is incorrect");
        }
        if (validPassword(password)) {
            throw new RegistrationException("Password is blank or has less than "
                    + PASSWORD_LENGTH + " characters");
        }
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("User with this email "
                    + email + " already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean validEmail(String email) {
        Pattern emailRegex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        return emailRegex.matcher(email).matches();
    }

    private boolean validPassword(String password) {
        return (password.isBlank() || password.length() < PASSWORD_LENGTH);
    }
}
