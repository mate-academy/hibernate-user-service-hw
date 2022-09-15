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
import org.apache.commons.validator.routines.EmailValidator;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        String emailNoWhitespace = email.strip();
        String passwordNoWhitespace = password.strip();
        Optional<User> userOptional = userService.findByEmail(emailNoWhitespace);
        User user = userOptional.orElseThrow(
                () -> new AuthenticationException("User or password is incorrect"));
        String hashedPassword = HashUtil.hashPassword(passwordNoWhitespace, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("User or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        String emailNoWhitespace = email.strip();
        String passwordNoWhitespace = password.strip();
        if (!EmailValidator.getInstance().isValid(emailNoWhitespace)) {
            throw new RegistrationException("Email '" + emailNoWhitespace + '\'' + " is not valid");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "User with email '" + emailNoWhitespace + '\'' + " is already exist");
        }
        if (passwordNoWhitespace.isEmpty()) {
            throw new RegistrationException("Password could not be empty");
        }
        User user = new User(emailNoWhitespace, passwordNoWhitespace);
        userService.add(user);
        return user;
    }
}
