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
                .orElseThrow(() -> new AuthenticationException("Email or password is incorrect"));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException("Can't register user, "
                                            + "check email and password you entered!");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This email has already been registered");
        }
        return userService.add(new User(password, email));
    }
}
