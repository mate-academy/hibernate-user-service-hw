package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() && isPasswordValid(password, userOptional.get())) {
            return userOptional.get();
        }
        throw new AuthenticationException("Login failed. Email or password you entered is incorrect");
    }


    @Override
    public User register(String email, String password) throws RegistrationException {
        checkEmail(email);
        return userService.add(new User(email, password));
    }

    private boolean isPasswordValid(String password, User user) {
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashPassword);
    }

    private void checkEmail(String email) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("Registration failed. Enter another email");
        }
    }
}
