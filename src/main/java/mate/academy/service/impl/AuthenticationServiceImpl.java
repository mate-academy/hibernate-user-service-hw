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
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty()) {
            throw new AuthenticationException("Email or password are incorrect!");
        }
        User newUser = userFromDB.get();
        String hashedPassword = HashUtil.hashPassword(password, newUser.getSalt());
        if (newUser.getPassword().equals(hashedPassword)) {
            return newUser;
        } else {
            throw new AuthenticationException("Email or password are incorrect!");
        }
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with current email exists! " + email);
        } else if (password.isEmpty()) {
            throw new RegistrationException("Password field can not be empty!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
