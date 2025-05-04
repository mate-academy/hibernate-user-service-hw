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
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()
                || !user.get().getPassword().equals(HashUtil.hashPassword(password,
                                user.get().getSalt()))) {
            throw new AuthenticationException("Wrong email or login!");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (email == null || password == null
                || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password should not be empty!");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exist!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
