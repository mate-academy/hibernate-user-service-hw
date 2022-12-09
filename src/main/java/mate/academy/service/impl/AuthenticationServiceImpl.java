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
        if (email.isBlank() || password.isBlank()) {
            throw new AuthenticationException("Cannot login user. "
                    + "Login or password is empty string or filled with whitespace. "
                    + "email=" + email);
        }
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Cannot authenticate user. email=" + email));
        if (!user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            throw new AuthenticationException("Cannot authenticate user. email=" + email);
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isBlank() || password.isBlank()) {
            throw new RegistrationException("Cannot register user. "
                    + "Login or password is empty string or filled with whitespace."
                    + " email=" + email);
        }
        try {
            return userService.add(new User(email, password));
        } catch (Exception e) {
            throw new RegistrationException("Cannot register user. "
                    + "Probably user already exists. email=" + email, e);
        }
    }
}
