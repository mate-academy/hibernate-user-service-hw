package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Dao;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Dao
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            String hashedPassword = HashUtil.hashPassword(password, user.get().getSalt());
            if (user.get().getPassword().equals(hashedPassword)) {
                return user.get();
            }
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User already exists");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Password cannot be empty");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        userService.add(user);
        return user;
    }
}
