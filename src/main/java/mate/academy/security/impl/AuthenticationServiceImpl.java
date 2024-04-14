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
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("email can not be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("password can not be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty() || !userFromDB.get().getPassword().equals(
                HashUtil.hashPassword(password, userFromDB.get().getSalt()))) {
            throw new AuthenticationException("There is a problem with user login");
        }
        return userFromDB.get();
    }
}
