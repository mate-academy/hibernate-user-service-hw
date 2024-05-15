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
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty() || !isValidPassword(user.get(), password)) {
            throw new AuthenticationException(
                    "Authentication failed for user with email: " + email);
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (!isValidUserToRegister(user, email, password)) {
            throw new RegistrationException("Register failed for user with email: " + email);
        }
        User currentUser = new User();
        currentUser.setEmail(email);
        currentUser.setPassword(password);
        userService.add(currentUser);
        return currentUser;
    }

    private boolean isValidPassword(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }

    private boolean isValidUserToRegister(Optional<User> userOptional,
                                          String email, String password) {
        return userOptional.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }
}
