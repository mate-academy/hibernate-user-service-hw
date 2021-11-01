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
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUserByEmail = userService.findByEmail(email);
        if (!optionalUserByEmail.isEmpty()) {
            User userDB = optionalUserByEmail.get();
            String currentPassword = HashUtil.hashPassword(password, userDB.getSalt());
            if (currentPassword.equals(userDB.getPassword())) {
                return userDB;
            }
        }
        throw new AuthenticationException("Wrong password of unexciting email :"
                + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || !isValid(email)) {
            throw new RegistrationException("Try another password or email :"
                    + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private static boolean isValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
