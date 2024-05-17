package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import java.util.Optional;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromOptional = userService.findByEmail(email);
        if (userFromOptional.isEmpty()) {
            throw new AuthenticationException("Cannot find user with mail:" + email);
        } else {
            User user = userFromOptional.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            } else {
                throw new AuthenticationException("Wrong password");
            }
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromOptional = userService.findByEmail(email);
        if (userFromOptional.isPresent()) {
            throw new RegistrationException("User already exists");
        } else if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password cannot be empty");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.add(user);
        }
    }
}
