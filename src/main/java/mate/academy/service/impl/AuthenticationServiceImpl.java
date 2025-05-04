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
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            User foundUser = user.get();
            if (HashUtil.hashPassword(password, foundUser.getSalt())
                    .equals(foundUser.getPassword())) {
                return foundUser;
            }
        }
        throw new AuthenticationException("Username or password invalid.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email, password);
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(user);
        }
        throw new RegistrationException("Email " + email + " already exists");
    }
}
