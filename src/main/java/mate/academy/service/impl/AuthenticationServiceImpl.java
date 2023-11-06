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
                .filter(user -> user.getPassword()
                        .equals(HashUtil.hashPassword(password, user.getSalt())))
                .orElseThrow(() -> new AuthenticationException("Can't authenticate user. "
                        + "Login or password incorrect!"));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("A user with this email ("
                    + email + ") already exists");
        }
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password fields shouldn't be empty");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
