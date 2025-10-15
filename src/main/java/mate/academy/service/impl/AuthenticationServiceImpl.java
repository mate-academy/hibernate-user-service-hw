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
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        User user = userFromDbOptional.get();
        String hashPassword = HashUtil.hashPassword(password, user.getSalt());
        if (userFromDbOptional.isEmpty() || !user.getPassword().equals(hashPassword)) {
            throw new AuthenticationException("User with email" + email + " doesn't"
                    + "exist in database or password is incorrect");
        }
        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty()) {
            throw new RegistrationException("Email is empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password is empty.");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email
                    + " already exists.");
        }
        if (!email.contains("@")) {
            throw new RegistrationException("Email doesn't contain an @ sign.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return user;
    }
}
