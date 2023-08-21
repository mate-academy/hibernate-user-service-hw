package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()
                || !isEqualPasswords(password, userFromDb.get())) {
            throw new AuthenticationException("Can't authenticate user by email:  " + email
                    + "Invalid email or password!");
        }
        return userFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email " + email
                    + ". This user already exist");
        }
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        return userService.add(user);
    }

    private boolean isEqualPasswords(String password, User userFromDb) {
        return HashUtil.hashPassword(password, userFromDb.getSalt()).equals(userFromDb.getPassword());
    }
}
