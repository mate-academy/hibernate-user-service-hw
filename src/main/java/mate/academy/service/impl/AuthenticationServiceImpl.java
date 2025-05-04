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
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isEmpty()) {
            User user = userOptional.get();
            user.getPassword().equals(HashUtil.hasPassword(password, user.getSalt()));
            return user;
        } else {
            throw new AuthenticationException("The password or login is invalid");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            User user = new User(email, password);
            userService.add(user);
            return user;
        } else {
            throw new RegistrationException("User already exists");
        }
    }
}
