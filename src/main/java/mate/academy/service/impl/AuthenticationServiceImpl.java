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
        User userFromDB = userService.findByEmail(email).get();
        String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
        if (userService.findByEmail(email).isPresent()
                && userFromDB.getPassword().equals(hashedPassword)) {
            return userFromDB;
        }
        throw new AuthenticationException("Can't authenticate user. ");
    }

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if ((!email.isEmpty() & !password.isEmpty()) && userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            return userService.save(user);
        }
        throw new RegistrationException("Can't register user. Try again! ");
    }
}
