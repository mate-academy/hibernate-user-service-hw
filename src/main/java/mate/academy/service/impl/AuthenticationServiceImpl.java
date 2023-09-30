package mate.academy.service.impl;

import java.util.Optional;
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
        if (!validateEmail(email)) {
            throw new AuthenticationException("Invalid email: " + email);
        }
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isEmpty() || !passwordsEquals(userFromDbOptional.get(), password)) {
            throw new AuthenticationException("Can't find user with email: " + email
                    + " or the password is incorrect");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User newUser = new User();
        if (!validateEmail(email)) {
            throw new RegistrationException("Email: " + email + " is invalid");
        }
        if (!validatePassword(password)) {
            throw new RegistrationException("Password must contain at least 8 symbols!");
        }
        newUser.setEmail(email);
        newUser.setPassword(password);
        try {
            return userService.add(newUser);
        } catch (Exception e) {
            throw new RegistrationException("Cant create user with email: " + email, e);
        }
    }

    private boolean validateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
        return email.matches(emailPattern);
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() >= 8;
    }

    private boolean passwordsEquals(User user, String password) {
        password = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(password);
    }
}
