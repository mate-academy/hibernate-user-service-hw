package mate.academy.service.impl;

import java.util.Objects;
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
    private static final String PATTERN_CHECK_EMAIL =
            "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final String PATTERN_CHECK_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{8,20}";

    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) throws RegistrationException {
        boolean matchesEmail = Pattern.matches(PATTERN_CHECK_EMAIL, email);
        boolean matchesPassword = Pattern.matches(PATTERN_CHECK_PASSWORD, password);

        if (!matchesPassword || !matchesEmail) {
            throw new RegistrationException("The password or email does not meet the minimum "
                    + "requirements.");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email: " + email + " is already registered.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            String hashedPassword = HashUtil.hashPassword(password, userFromDB.get().getSalt());
            if (Objects.equals(userFromDB.get().getPassword(), hashedPassword)) {
                return userFromDB.get();
            }
        }
        throw new AuthenticationException("Can't find user by email: " + email
                + " or password is wrong.");
    }
}
