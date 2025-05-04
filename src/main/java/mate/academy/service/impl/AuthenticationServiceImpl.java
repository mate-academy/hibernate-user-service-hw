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
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String hashPassword = HashUtil.hashPassword(password, user.getSalt());

            if (user.getPassword().equals(hashPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Can't authenticate user. "
                + "Check your email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password can't be empty or null!");
        }
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new RegistrationException("User with such login already exists!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }
}
