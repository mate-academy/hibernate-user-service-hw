package mate.academy.service.impl;

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
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        validateInput(email, EMAIL);
        validateInput(password, PASSWORD);

        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("User with email " + email + " not found"));

        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Can't authenticate a user, wrong password");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        validateInput(email, EMAIL);
        validateInput(password, PASSWORD);
        isUserPresent(email);
        return userService.add(new User(email, password));
    }

    private void isUserPresent(String email) {
        userService.findByEmail(email).ifPresent(user -> {
            throw new RegistrationException("User with email " + email + " is already registered");
        });
    }

    public static void validateInput(String input, String field) {
        if (input == null || input.isEmpty()) {
            throw new RegistrationException("Can't register a user, wrong " + field);
        }
    }
}
