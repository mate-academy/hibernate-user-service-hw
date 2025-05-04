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
        Optional<User> user = userService.findByLogin(login);
        if (user.isPresent() && passwordVerification(password, user.get())) {
            return user.get();
        }
        throw new AuthenticationException("Wrong password!");
    }

    @Override
    public User register(String login, String password) throws RegistrationException {
        if (userService.findByLogin(login).isPresent()) {
            throw new RegistrationException("This login: " + login + " already exist!");
        }
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        return userService.add(newUser);
    }

    public boolean passwordVerification(String inputPassword, User user) {
        String hashedPassword = HashUtil.hashPassword(inputPassword, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
