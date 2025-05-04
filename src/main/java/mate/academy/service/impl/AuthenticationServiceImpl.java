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
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    @Inject
    private UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDatabase = userService.findByEmail(email);
        if (userFromDatabase.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user: "
                    + "unable to find user by email " + email);
        }
        User user = userFromDatabase.get();
        String inputHashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(inputHashedPassword)) {
            return user;
        } else {
            throw new AuthenticationException("Can't authenticate user: incorrect input data");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (emailMatcher.matches()) {
            user.setEmail(email);
        } else {
            throw new RegistrationException("Email contains incorrect symbols");
        }
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        if (passwordMatcher.matches()) {
            user.setPassword(password);
        } else {
            throw new RegistrationException("Password contains incorrect symbols "
                    + "or length less than 8 or greater than 20");
        }
        return userService.save(user);
    }
}
