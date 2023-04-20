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
        if (password.isEmpty()
                || !HashUtil.hashPassword(password,
                user.get().getSalt()).equals(user.get().getPassword())) {
            throw new AuthenticationException("Email or password are incorrect");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email or password can't be null");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
