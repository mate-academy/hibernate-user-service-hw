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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent() && checkPassword(password, userFromDbOptional.get())) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Wrong email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()) {
            throw new RegistrationException("User with this " + email + " is already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean checkPassword(String rawPassword, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(rawPassword, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
