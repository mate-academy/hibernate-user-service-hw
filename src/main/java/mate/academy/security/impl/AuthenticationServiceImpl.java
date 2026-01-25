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

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (email == null || password == null
                || email.isBlank() || password.isBlank()) {
            throw new AuthenticationException("Invalid email or password");
        }

        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isEmpty()
                || !optionalUser.get().getPassword().equals(
                HashUtil.hashPassword(password, optionalUser.get().getSalt()))) {
            throw new AuthenticationException("Invalid email or password");
        }

        return optionalUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null
                || password == null
                || email.isBlank()
                || password.isBlank()) {
            throw new RegistrationException(
                    "Email or password must be provided"
            );
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(
                    "Email already exists"
            );
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }
}
