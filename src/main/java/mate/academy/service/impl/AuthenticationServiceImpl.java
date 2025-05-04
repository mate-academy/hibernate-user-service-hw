package mate.academy.service.impl;

import jakarta.transaction.Transactional;
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
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isEmpty() || !userFromDB.get().getPassword().equals(
                HashUtil.hashPassword(password, userFromDB.get().getSalt()))) {
            throw new AuthenticationException("Cannot login user with email: " + email);
        }
        return userFromDB.get();
    }

    @Transactional
    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        validateUserPresence(userFromDB, email);
        validatePassword(password);
        User user = new User(email, password);
        return userService.add(user);
    }

    public void validateUserPresence(Optional<User> userFromDB, String email)
            throws RegistrationException {
        if (userFromDB.isPresent()) {
            throw new RegistrationException("User with email " + email + " is already registered");
        }
    }

    public void validatePassword(String password) throws RegistrationException {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password cannot be empty");
        }
    }
}
