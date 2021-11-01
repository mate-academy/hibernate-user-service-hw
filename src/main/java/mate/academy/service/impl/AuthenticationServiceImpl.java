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
        User userFromDb = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("There are no User with email: " + email + " in DB"));
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        if (hashedPassword.equals(userFromDb.getPassword())) {
            return userFromDb;
        }
        throw new AuthenticationException("Password are incorrect for email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("User with email: " + email + " is already registered");
    }
}
