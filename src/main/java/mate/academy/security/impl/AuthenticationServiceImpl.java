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
        if (userFromDbOptional.isEmpty() || !userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        Optional<User> userToDB = userService.findByLogin(login);
        if (userToDB.isPresent() || login.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("The login ( " + login + ") already exists."
                    + " Try another.");
        }
        return userService.add(new User(login, password));
    }
}
