package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.PasswordHashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && isValidPassword(user.get(), password)) {
            return user.get();
        }
        throw new AuthenticationException(
                "User has not been found or password doesn't match. User: " + user);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()) {
            throw new RegistrationException("Can't register user with email '" + email
                                            + "'. Email has been already registered");
        }
        User userToRegister = new User();
        userToRegister.setEmail(email);
        userToRegister.setPassword(password);
        return userService.add(userToRegister);
    }

    private boolean isValidPassword(User user, String inputPassword) {
        String hashedPassword = PasswordHashUtil.hashPassword(inputPassword, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
