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
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Wrong login or password");
        }
        User user = optionalUser.get();
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
            return user;
        }
        throw new AuthenticationException("Wrong login or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isBlank() || password.isBlank()) {
            throw new RegistrationException("Wrong input data");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
