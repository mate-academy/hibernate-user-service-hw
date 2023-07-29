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
        Optional<User> userFromDbOptional = userService.findByLogin(login);
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user");
        }
        User user = userFromDbOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        Optional<User> userToDB = userService.findByEmail(login);
        if (userToDB.isPresent() || login.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("The login ( " + login + ") already exists."
                    + " Try another.");
        }
        return userService.add(new User(login, password));
    }
}
