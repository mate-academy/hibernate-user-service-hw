package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password)
            throws AuthenticationException {
        return userService.findByEmail(email).orElseThrow(()
                -> new AuthenticationException("Your email or password is incorrect!"));
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User newUser = userService.add(user);
        if (newUser == null) {
            throw new RegistrationException("Can not register user!");
        }
        return newUser;
    }
}
