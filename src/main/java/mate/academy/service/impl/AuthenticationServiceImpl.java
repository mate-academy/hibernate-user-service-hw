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
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Password or login doesn't match"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        }
        throw new AuthenticationException("Password or login doesn't match");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password == null || email == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Invalid login or password ");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already exist " + email);
        }
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        User newUser = new User(email, hashedPassword);
        newUser.setSalt(salt);
        return userService.add(newUser);
    }
}
