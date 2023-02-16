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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        User user = userByEmail.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashPassword)) {
            return user;
        }
        throw new AuthenticationException("Email or password is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password can't be empty");
        }
        User user = new User(email, password);
        userService.add(user);
        return user;
    }
}
