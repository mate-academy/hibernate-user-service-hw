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
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            throw new AuthenticationException("There is no such user with email: " + email);
        }
        User user = userFromDb.get();
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return user;
        }
        throw new AuthenticationException("Wrong Password");
    }

    @Override
    public void register(String email, String password) throws RegistrationException {
        if (email == null || password == null) {
            throw new RegistrationException("Invalid email or password");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
    }
}
