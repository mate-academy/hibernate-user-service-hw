package mate.academy.service.impl;

import mate.academy.Main;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final int MIN_LENGTH_PASSWORD = 8;

    @Inject
    private UserService userService;
    private User user;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()
                || !userOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password,userOptional.get().getSalt()))) {
            throw new AuthenticationException("User not Present in Db");
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || userService.findByEmail(email).isPresent()
        || email.contains("@")) {
            throw new RegistrationException("Email is incorrect or is Present in DB email= "
                    + email);
        } else if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Password is < 8 symbols = " + password);
        }
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
