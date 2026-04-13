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
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new AuthenticationException("Email is null or empty");
        }
        String clearEmail = email.trim().toLowerCase();
        Optional<User> userFromDbOptional = userService.findByEmail(clearEmail);
        if (userFromDbOptional.isEmpty() || !userFromDbOptional.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            throw new AuthenticationException("Email or Password are incorrect");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Email is null or empty");
        }
        String clearEmail = email.trim().toLowerCase();
        if (userService.findByEmail(clearEmail).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exist");
        }
        User newUser = new User();
        newUser.setEmail(clearEmail);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
