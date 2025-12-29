package mate.academy.service.impl;

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
        User user = userService.findByEmail(email).orElse(null);
        String hashedInputPassword
                = user == null ? null : HashUtil.hashPassword(password, user.getSalt());
        if (user == null || !user.getPassword().equals(hashedInputPassword)) {
            throw new AuthenticationException("Invalid email or password: " + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty()) {
            throw new RegistrationException("Email is empty: " + email);
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password is empty: " + password);
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists: " + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
