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
        Optional<User> byEmail = userService.findByEmail(email);
        if (email == null || password == null || byEmail.isEmpty()
                || !byEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password, byEmail.get().getSalt()))) {
            throw new AuthenticationException("Username or password was incorrect");
        }
        return byEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Email and password should be not null");
        }
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("User with this email already exist");
        }
        byte[] salt = HashUtil.getSalt();
        User user = new User(email, password);
        return userService.add(user);
    }
}

