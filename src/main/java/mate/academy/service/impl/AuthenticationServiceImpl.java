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
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Email or password is incorrect"));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!hashedPassword.equals(user.getHashedPassword())) {
            throw new AuthenticationException("Email or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!validateEmail(email)) {
            throw new RegistrationException("Invalid email");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with this email already exists");
        }
        if (!validatePassword(password)) {
            throw new RegistrationException("Password too short");
        }
        byte[] salt = HashUtil.getSalt();
        String hashedPassword = HashUtil.hashPassword(password, salt);
        User user = new User();
        user.setEmail(email);
        user.setHashedPassword(hashedPassword);
        user.setSalt(salt);
        return userService.add(user);
    }

    private static boolean validateEmail(String email) {
        return email.matches("^[\\w\\.-]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    private static boolean validatePassword(String password) {
        return password.length() > 3;
    }
}
