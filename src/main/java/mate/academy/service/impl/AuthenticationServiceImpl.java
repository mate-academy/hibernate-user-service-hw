package mate.academy.service.impl;

import java.util.Optional;
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
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(login);
        if (userFromDb.isEmpty() || !userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDb.get().getSalt()))) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(login);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("Can't registered user. User with login: "
                    + login + " already exist in DB");
        }
        return userService.add(new User(login, password));
    }
}
