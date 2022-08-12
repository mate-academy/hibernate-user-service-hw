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
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()
                && userOptional.get()
                .getPassword()
                .equals(HashUtil.hashPassword(password, userOptional.get().getSalt()))) {
            return userOptional.get();
        } else {
            throw new AuthenticationException("Incorrect email or password");
        }
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password.isEmpty() || email.isEmpty()) {
            throw new RegistrationException(String.format(
                    "Password or email must not be an empty string", email));
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format(
                    "User with email %s already exist", email));
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
