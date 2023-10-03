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
        return userService.findByEmail(email).filter(user ->
                user.getPassword().equals(HashUtil.hashPassword(password,
                        user.getSalt()))).orElseThrow(() ->
                new AuthenticationException("Login or password is wrong!"));
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent() || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Couldn`t create user or user already exist!");
        }
        return userService.add(new User(email, password));
    }
}
