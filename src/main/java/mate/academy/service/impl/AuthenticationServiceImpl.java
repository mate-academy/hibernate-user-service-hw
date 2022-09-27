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
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isPresent() && HashUtil.hashPassword(password, byEmail.get().getSalt())
                .equals(byEmail.get().getPassword())) {
            return byEmail.get();
        }
        throw new AuthenticationException("Pair 'email - password' is incorect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password can't be empty");
        }
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("User with email: " + email + " exists");
    }
}
