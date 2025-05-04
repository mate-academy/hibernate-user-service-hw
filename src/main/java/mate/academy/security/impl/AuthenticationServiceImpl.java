package mate.academy.security.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public void register(String email, String password) throws RegistrationException {
        validateEmail(email);
        validatePassword(password);
        ensureUserDoesNotExist(email);
        createUserAndSave(email, password);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        validatePasswordForLogin(password);
        return authenticateUser(email, password);
    }

    private void validateEmail(String email) throws RegistrationException {
        if (email == null || email.trim().isEmpty()) {
            throw new RegistrationException("Email cannot be null or empty.");
        }
    }

    private void validatePassword(String password) throws RegistrationException {
        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty.");
        }
    }

    private void ensureUserDoesNotExist(String email) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists.");
        }
    }

    private void createUserAndSave(String email, String password) {
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.save(user);
    }

    private static void validatePasswordForLogin(String password) throws AuthenticationException {
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Password cannot be null or empty.");
        }
    }

    private User authenticateUser(String email, String password) throws AuthenticationException {
        var optionalUserFromDB = userService.findByEmail(email);
        if (optionalUserFromDB.isEmpty() || !HashUtil.hashPassword(password,
                        optionalUserFromDB.get().getSalt())
                .equals(optionalUserFromDB.get().getPassword())) {
            throw new AuthenticationException("Incorrect login or password");
        }
        return optionalUserFromDB.get();
    }
}
