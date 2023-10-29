package mate.academy.service.impl;

import java.util.Optional;
import java.util.regex.Matcher;
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
    @Inject
    private static UserService userService;

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])"
            + "(?=.*[a-z])"
            + "(?=.*[A-Z])"
            + "(?=.*[@#$%^&-+=()])"
            + "(?=\\S+$).{8,20}$";
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public User login(String email, String password) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isEmpty()
                && HashUtil.hashPassword(password,
                userOptional.get().getSalt()).equals(userOptional.get().getPassword())) {
            return userOptional.get();
        }
        throw new AuthenticationException("Cant authenticate User: "
                + "login or password are incorrect. ");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userOptional = userService.findByEmail(email);
        checkIfEmailMatchesPattern(email);
        checkIfEmailAlreadyExist(userOptional, email);
        checkIfPasswordMatchesPattern(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return userService.findByEmail(email).get();
    }

    private void checkIfEmailMatchesPattern(String email) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = p.matcher(email);
        if (email.isEmpty() || !matcher.matches()) {
            throw new RegistrationException("Can't create a new User: email is incorrect - "
                    + email);
        }
    }

    private void checkIfEmailAlreadyExist(Optional<User> userOptional, String email) {
        if (!userOptional.isEmpty()) {
            throw new RegistrationException("Can't create a new User. User with email "
                    + email + " is already exist!");
        }
    }

    private void checkIfPasswordMatchesPattern(String password) {
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = p.matcher(password);
        if (password.isEmpty() || !matcher.matches()) {
            throw new RegistrationException("Can't create a new User: password is incorrect!\n"
                    + "Password must contain:\n"
                    + "It contains at least 8 characters and at most 20 characters.\n"
                    + "It contains at least one digit.\n"
                    + "It contains at least one upper case alphabet.\n"
                    + "It contains at least one lower case alphabet.\n"
                    + "It contains at least one special character which includes !@#$%&*()-+=^.\n"
                    + "It doesn’t contain any white space.");
        }
    }
}
