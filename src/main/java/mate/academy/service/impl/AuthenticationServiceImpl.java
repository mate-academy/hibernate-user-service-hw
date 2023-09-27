package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(login);

        if (userOptional.isPresent() && passwordValid(userOptional.get(), password)) {
            return userOptional.get();
        }

        throw new AuthenticationException("Password or login wrong or user doesnt exist!");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByEmail(login).isPresent()) {
            throw new RegistrationException("There is already a user with the login: " + login);
        }
        User user = new User("Den", login, password);
        return userService.add(user);
    }

    private boolean passwordValid(User user, String password) {
        return password.isEmpty()
                && user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}

