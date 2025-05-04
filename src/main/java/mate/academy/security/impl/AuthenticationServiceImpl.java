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
    public User login(String email, String password) throws AuthenticationException {
        if (isEmailOrPasswordInvalid(email, password)) {
            throw new AuthenticationException("Invalid email or password");
        }
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(String.format(
                        "User with email %s not found", email
                )));
        byte[] salt = user.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Incorrect password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (isEmailOrPasswordInvalid(email, password)) {
            throw new RegistrationException("Invalid email or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format(
                    "User with email %s already exist", email
            ));
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean isEmailOrPasswordInvalid(String email, String password) {
        return email == null || email.isBlank()
                || password == null || password.isBlank();
    }
}
