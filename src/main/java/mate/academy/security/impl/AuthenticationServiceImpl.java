package mate.academy.security.impl;

import jakarta.transaction.Transactional;
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
    @Transactional
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email can not be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password can not be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("User not found by email: " + email));
        if (!user.getPassword().equals(
                HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("There is incorrect password");
        }
        return user;
    }
}
