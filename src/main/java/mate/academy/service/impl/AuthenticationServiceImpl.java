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
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && matchPasswords(email, userFromDB.get())) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Cannot authenticate user by email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateUser(email, password);
        return userService.add(new User(email, password));
    }

    private void validateUser(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("Cannot register user with email: " + email);
        }
    }

    private boolean matchPasswords(String password, User userFromDb) {
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.getSalt());
        return hashedPassword.equals(userFromDb.getPassword());
    }
}
