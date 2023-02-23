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
        Optional<String> errorMsg = validateInputAndGetErrorMsg(email, password);
        if (errorMsg.isPresent()) {
            throw new AuthenticationException(errorMsg.get());
        }
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()) {
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        throw new AuthenticationException("Incorrect login and/or password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<String> errorMsg = validateInputAndGetErrorMsg(email, password);
        if (errorMsg.isPresent()) {
            throw new RegistrationException(errorMsg.get());
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " is already registered.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private Optional<String> validateInputAndGetErrorMsg(String email, String password) {
        if (email == null || email.isEmpty()) {
            return Optional.of("Field email cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            return Optional.of("Field password cannot be empty.");
        }
        return Optional.empty();
    }
}
