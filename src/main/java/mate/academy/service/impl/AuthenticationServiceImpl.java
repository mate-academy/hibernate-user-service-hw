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
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByLogin(email).isEmpty()) {
            return userService.save(new User(email, password));
        }
        throw new RegistrationException("User with email" + email + "already exists");
    }

    @Override
    public User login(String login, String password) {
        Optional<User> user = userService.findByLogin(login);
        if (user.isEmpty() || !user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            throw new AuthenticationException("Can`t authenticate user with login: " + login);
        }
        return user.get();
    }
}
