package mate.academy.security.impl;

import java.util.Optional;
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
            throw new RegistrationException("User already exists!");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user. "
                + "Email or password is incorrect!");
    }

    private void isValidEmail(String email) {
        if (email == null
                || !email.contains("@")) {
            throw new DataValidationException("Email isn't valid");
        }
    }

    private void isValidPassword(String password) {
        if (password == null
                || password.isBlank()) {
            throw new DataValidationException("Password cannot be empty");
        }
    }
}
