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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() && validatePassword(password, userFromDb.get())) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Wrong login or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()
                && userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Incorrect email or password");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    private boolean validatePassword(String password, User user) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
