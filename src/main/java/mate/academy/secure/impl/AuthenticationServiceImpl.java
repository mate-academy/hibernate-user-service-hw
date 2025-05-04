package mate.academy.secure.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.secure.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email == null || password == null) {
            throw new AuthenticationException("Incorrect data entered "
                    + "(null was passed as a parameter).");
        }
        Optional<User> user = userService.findByEmail(email);
        if (userService.findByEmail(email).isEmpty()
                || !HashUtil.hashPassword(password, user.get().getSalt())
                .equals(user.get().getPassword())) {
            throw new AuthenticationException("Email or password incorrect!! Email " + email);
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null) {
            throw new RegistrationException("Incorrect data entered "
                    + "(null was passed as a parameter).");
        }
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Incorrect data entered "
                    + "(at least one field is empty).");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.generateSalt());
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }
}
