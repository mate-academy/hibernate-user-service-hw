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
        Optional<User> userFromDbOption = userService.findByEmail(email);
        if (userFromDbOption.isPresent()) {
            User user = userFromDbOption.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Authentication fail! Email or password isn't correct!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> registedByEmail = userService.findByEmail(email);
        if (registedByEmail.isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("User already registered");
    }
}
