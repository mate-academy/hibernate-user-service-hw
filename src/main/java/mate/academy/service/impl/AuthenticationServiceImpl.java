package mate.academy.service.impl;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.HashUtil;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        if (isEmailOrPasswordInvalid(email, password)) {
            throw new AuthenticationException("Invalid email or password");
        }
        User userFromDb = userService.findByEmail(email)
                                     .orElseThrow(() -> new AuthenticationException(
                                             "User with email=" + email + " not exist"));
        byte[] salt = userFromDb.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        if (userFromDb.getPassword().equals(hashedPassword)) {
            return userFromDb;
        }
        throw new AuthenticationException("Wrong password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (isEmailOrPasswordInvalid(email, password)) {
            throw new RegistrationException("Invalid email or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exist");
        }
        User newUser = new User(email, password);
        return userService.add(newUser);
    }

    private boolean isEmailOrPasswordInvalid(String email, String password) {
        return email == null || email.isBlank()
                || password == null || password.isBlank();
    }
}
