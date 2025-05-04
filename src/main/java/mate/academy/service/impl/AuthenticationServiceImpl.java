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
        if (userFromDbOptional.isEmpty() || !passwordMatches(userFromDbOptional.get(), password)) {
            throw new AuthenticationException(("Invalid email or password."
                    + " Please check your credentials."));
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() && password.isEmpty() || isEmailRegistered(email)) {
            throw new RegistrationException("Not valid data or User with this"
                    + " email is already registered.");
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }

    private boolean passwordMatches(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }

    private boolean isEmailRegistered(String email) {
        Optional<User> userByEmail = userService.findByEmail(email);
        return userByEmail.isPresent();
    }
}
