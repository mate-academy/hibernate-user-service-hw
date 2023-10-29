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
        if (user.isEmpty() || !matchPasswords(user.get(), password)) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email %s already exists".formatted(email));
        }
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email can't be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password can't be empty");
        }
        return userService.add(new User(email, password));
    }

    private boolean matchPasswords(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
