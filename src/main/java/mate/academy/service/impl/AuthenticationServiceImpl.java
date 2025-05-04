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
        User user = userService.findByEmail(email).orElseThrow(()
                -> new AuthenticationException("Can't find account for: " + email));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (hashedPassword.equals(user.getPassword())) {
            return user;
        } else {
            throw new AuthenticationException("Incorrect password");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (isBlank(email) || isBlank(password)) {
            throw new RegistrationException("Invalid login or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with " + email + " email is present");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
