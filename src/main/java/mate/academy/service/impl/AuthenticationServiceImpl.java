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
        if (user.isEmpty()
                || !comparePassword(user.get(), password)) {
            throw new AuthenticationException("Email or password are incorrect");
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new RegistrationException("Email can't be empty");
        }
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new RegistrationException("Password can't be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format("Email %s already used", email));
        }
        return userService.add(new User(email, password));
    }

    private boolean comparePassword(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
