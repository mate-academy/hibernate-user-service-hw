package mate.academy.service.impl;

import static mate.academy.util.HashUtil.hashPassword;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()
                || !password.equals(hashPassword(password, userOptional.get().getSalt()))) {
            throw new AuthenticationException("Can't login user with email " + email);
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() || password.isEmpty()) {
            throw new RegistrationException("Can't register user with email " + email);
        }
        return userService.add(new User(email, password));
    }
}
