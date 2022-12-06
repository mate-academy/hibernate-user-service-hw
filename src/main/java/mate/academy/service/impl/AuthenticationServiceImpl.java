package mate.academy.service.impl;

import java.util.Optional;
import javax.naming.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final String REGEX_PATTERN = "^(.+)@(\\w+)$";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        System.out.println(user.get().getPassword());
        System.out.println(password);
        user.orElseThrow(AuthenticationException::new);
        String hashedPassword = HashUtils.hashPassword(password, user.get().getSalt());
        if (!user.get().getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Invalid login or password");
        } else {
            return user.get();
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateUser(email, password);
        User user = new User(email,password);
        return userService.add(user);
    }

    public boolean validateUser(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("Unable to register with current email: " + email);
        }
        if (!checkValidEmail(email)) {
            throw new RegistrationException("Not valid email format " + email);
        }
        if (!checkValidPassword(password)) {
            throw new RegistrationException(
                    "Password length isnt correct.Min length of password: " + PASSWORD_MIN_LENGTH);
        }
        return true;
    }

    public boolean checkValidEmail(String email) {
        return email.matches(REGEX_PATTERN);
    }

    public boolean checkValidPassword(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH;
    }
}
