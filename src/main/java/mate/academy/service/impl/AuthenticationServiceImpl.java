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
    public User login(String email, String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            String hashPassword = HashUtil.hashPassword(password, user.get().getSalt());
            if (hashPassword.equals(user.get().getPassword())) {
                return user.get();
            }
        }
        throw new AuthenticationException("Ca`nt login, incorrect password or email: " + email);
    }

    @Override
    public User register(String email, String password) {
        if (userService.findByEmail(email).isEmpty()) {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            userService.add(user);
            return user;
        }
        throw new RegistrationException("Ca`nt register user with this email, "
                + "user already exit: " + email);
    }
}
