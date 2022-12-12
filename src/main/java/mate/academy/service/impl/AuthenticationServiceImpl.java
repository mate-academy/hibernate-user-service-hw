package mate.academy.service.impl;

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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (!email.isBlank() && !password.isBlank()) {
            User user = userService.findByEmail(email).orElse(null);
            if (user != null && user.getPassword()
                    .equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
            }
        }
        throw new AuthenticationException("Cannot authenticate user. email=" + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!email.isBlank() && !password.isBlank() && userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("Cannot register user. email=" + email);
    }
}
