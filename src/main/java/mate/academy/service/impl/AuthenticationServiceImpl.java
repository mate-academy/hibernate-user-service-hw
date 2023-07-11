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
        return userService.findByEmail(email)
                .filter(u -> u.getPassword()
                        .equals(HashUtil.hashPassword(password, u.getSalt())))
                .orElseThrow(() -> new AuthenticationException("Email or password are incorrect"));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Can't register user");
        }
        return userService.add(new User(password, email));
    }
}
