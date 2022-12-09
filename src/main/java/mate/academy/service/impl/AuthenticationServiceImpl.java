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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (isCorrectPasswordAndDbContainsUser(password, userFromDbOptional)) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Email or password was incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            throw new RegistrationException("There is user with email " + email);
        }
        if (password == null
                || password.isEmpty()) {
            throw new RegistrationException("Enter the password please");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private static boolean isCorrectPasswordAndDbContainsUser(String password,
                                                              Optional<User> userFromDbOptional) {
        return userFromDbOptional
                .map(u -> u.getPassword().equals(HashUtil.hashPassword(password, u.getSalt())))
                .orElse(false);
    }
}
