package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;
import mate.academy.util.Validator;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userDB = userService.findByEmail(email);
        if (!userDB.isPresent()) {
            throw new AuthenticationException("User doesn't exist with email: " + email);
        }

        User user = userDB.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Incorrect password for user with email: " + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateNewUser(email);
        validateEmail(email);
        validatePassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private void validateNewUser(String email) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists. Email: " + email);
        }
    }

    private void validateEmail(String email) throws RegistrationException {
        if (!Validator.validateEmail(email)) {
            throw new RegistrationException("Email isn't valid. Email: " + email);
        }
    }

    private void validatePassword(String password) throws RegistrationException {
        if (!Validator.validatePassword(password)) {
            throw new RegistrationException("Password isn't valid. Password: " + password);
        }
    }
}
