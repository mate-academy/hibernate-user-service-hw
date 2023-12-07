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
        Optional<User> userFromDb = userService.findByEmail(email);
        User user = userFromDb.orElseThrow(() ->
                new RuntimeException("Can't get user from userFromDbOptional"));
        String userPasswordFromDb = user.getPassword();
        String inputPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userPasswordFromDb.equals(inputPassword)) {
            return user;
        }
        throw new AuthenticationException("Password or email is incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with given email already exist Email: ");
        }
        if (email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Can't register user with current credentials.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
