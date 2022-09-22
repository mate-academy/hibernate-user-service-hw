package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException("Incorrect pair of email & password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("The password and Email can't be empty.");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email is in the base");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));
        userService.add(newUser);
        return newUser;
    }
}
