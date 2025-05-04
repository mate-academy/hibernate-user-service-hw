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
        if (userFromDbOptional.isEmpty()
                || userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Username or password was incorrect");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Can't register this user");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " is already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
