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
    private static final String EMAIL_REGEX = ".+@\\w+\\.\\w+";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent() && arePasswordsEqual(userFromDbOptional.get(), password)) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Email or password is invalid");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateData(email, password);
        return userService.add(new User(email, password));
    }

    private void validateData(String email, String password) throws RegistrationException {
        if (!isPasswordValid(password)) {
            throw new RegistrationException("Invalid password.");
        }
        if (!isEmailValid(email)) {
            throw new RegistrationException("Invalid email format");
        }
        if (!isEmailUnique(email)) {
            throw new RegistrationException("User is already registered with email " + email);
        }
    }

    private boolean arePasswordsEqual(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }

    private boolean isPasswordValid(String password) {
        return !(password == null || password.isEmpty());
    }

    private boolean isEmailValid(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private boolean isEmailUnique(String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        return optionalUser.isEmpty();
    }
}
