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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && checkPassword(userOptional, password)) {
            return userOptional.get();
        }
        throw new AuthenticationException("Couldn't authenticate user. "
                + "Email or password is invalid");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty()) {
            throw new RegistrationException("Password couldn't be empty");
        }
        if (userService.findByEmail(email).isEmpty() && !password.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
        throw new RegistrationException("User with the same email: " + email
                + " already registered");
    }

    private static boolean checkPassword(Optional<User> userOptional, String password) {
        return userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()));
    }
}
