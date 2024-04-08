package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(login);
        if (userFromDB.isPresent()) {
            User existUser = userFromDB.get();
            String hashedPassword = HashUtil.hashPassword(password, existUser.getSalt());
            if (existUser.getPassword().equals(hashedPassword)) {
                return existUser;
            }
        }
        throw new AuthenticationException("Can't authenticate user: Provided wrong "
                                            + "login or password");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByEmail(login).isPresent()) {
            throw new RegistrationException("User: " + login + " is already registered.");
        }
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
