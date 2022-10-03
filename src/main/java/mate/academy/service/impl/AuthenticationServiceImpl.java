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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent() && checkPassword(optionalUser.get(), password)) {
            return optionalUser.get();
        }
        throw new AuthenticationException("Wrong credentials, please try again.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RegistrationException("This email is unavailable, please try another one.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }

    private boolean checkPassword(User user, String password) {
        return user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()));
    }
}
