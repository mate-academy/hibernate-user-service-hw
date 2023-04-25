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
    public User register(String email, String password)
            throws RegistrationException, AuthenticationException {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            return userService.add(user);
        } else {
            throw new RegistrationException("This email is already registered.");
        }
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new AuthenticationException("Your email is not registered... Please register!");
        }
        User user = byEmail.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("your password is incorrect!");
    }
}
