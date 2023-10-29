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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        validateEmailAndPassword(email, password);
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateEmailAndPassword(email, password);
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("A user with email address "
                    + email + " already exists");
        }
        return userService.add(new User(email, password));
    }

    private void validateEmailAndPassword(String email, String password) {
        if (email == null) {
            throw new RuntimeException("Email can't be null");
        }
        if (password == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (email.isEmpty()) {
            throw new RuntimeException("Email can't be empty");
        }
        if (password.isEmpty()) {
            throw new RuntimeException("Password can't be empty");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(email).matches()) {
            throw new RuntimeException("Email is invalid");
        }
        String passwordRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        if (!passwordPattern.matcher(password).matches()) {
            throw new RuntimeException("""
                    The password must contain:
                    1) At least 8 characters and no more than 20 characters
                    2) At least one digit
                    3) At least one uppercase letter
                    4) At least one lowercase letter
                    5) At least one special character, including !@#$%&*()-+=^
                    6) The password must not contain spaces""");
        }
    }
}
