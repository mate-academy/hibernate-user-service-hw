package mate.academy.authentication;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String EMAIL_VALIDATION_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            System.out.println("Login successful");
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect login or password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Pattern pattern = Pattern.compile(EMAIL_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new RegistrationException("E-mail should match following restrictions: "
                    + "only characters, numbers and symbols like '.', '-' and '_' are "
                    + "allowed to be used in e-mails");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this e-mail already exists."
                    + "e-mail: " + email);
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password can't be empty!");
        }
        User userToRegister = new User();
        userToRegister.setLogin(email);
        userToRegister.setPassword(password);
        userService.add(userToRegister);
        System.out.println("Registration successful");
        return userToRegister;
    }
}
