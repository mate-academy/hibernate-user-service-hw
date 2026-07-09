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
    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@"
            + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        credentialsCheck(email, password);
        Optional<User> findedUserFromDbOptional = userService.findByEmail(email);
        if (findedUserFromDbOptional.isEmpty()) {
            throw new AuthenticationException("User does not exist");
        }
        User user = findedUserFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Password is not correct.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        credentialsCheck(email, password);
        return userService.add(new User(email, password));
    }

    private void credentialsCheck(String email, String password) {
        if (email == null) {
            throw new RuntimeException("Email is null");
        }

        if (!emailPattern.matcher(email).matches()) {
            throw new RuntimeException("Email is invalid");
        }

        if (password == null) {
            throw new RuntimeException("Password is null");
        }
        if (password.isEmpty()) {
            throw new RuntimeException("Password is empty");
        }
    }
}
