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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty() || !passwordValidator(userFromDb.get(), password)) {
            throw new AuthenticationException("Email or password is invalid");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            return userService.add(newUser);
        }
        throw new RegistrationException("Email " + email + " is already registered");
    }

    private boolean passwordValidator(User user, String passwordForValidation) {
        String hashedPassword = HashUtil.hashPassword(passwordForValidation, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
