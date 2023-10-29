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
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            throw new AuthenticationException("The entered data is invalid (login or password)!");
        }
        if (userService.findByEmail(email).isEmpty()) {
            throw new AuthenticationException("Cannot find a user with the data!");
        }
        User user = userService.findByEmail(email).get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("The entered data is invalid (login or password)!");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            throw new RegistrationException("The entered data is invalid (login or password)!");
        }
        return userService.add(new User(email, password));
    }

    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }
}
