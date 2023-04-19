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
    public User login(String email, String password)
            throws AuthenticationException {
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Email of password are invalid"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Email of password are invalid");
        }
        return user;
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email of password are invalid");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists with this email = " + email);
        }
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        User newUser = new User(email, hashedPassword);
        newUser.setSalt(salt);
        return userService.add(newUser);
    }
}
