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
        Optional<User> fromDb = userService.findByEmail(email);
        if (fromDb.isEmpty() || !isValidPassword(fromDb.get(), password)) {
            throw new AuthenticationException("Authentication failed");
        }
        return fromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateRegisterData(email, password);
        return userService.add(new User(email, password));
    }

    private boolean isValidPassword(User user, String password) {
        return password != null && user.getPassword()
                .equals(HashUtil.hashPassword(password, user.getSalt()));
    }

    private void validateRegisterData(String email, String password) throws RegistrationException {
        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            throw new RegistrationException("Email or password is blank");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
    }
}
