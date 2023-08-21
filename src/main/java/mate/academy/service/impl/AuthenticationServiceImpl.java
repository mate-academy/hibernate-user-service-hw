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
        if (userOptional.isEmpty()
                || password.equals(HashUtil.hashPassword(
                        userOptional.get().getPassword(), userOptional.get().getSalt()))) {
            throw new AuthenticationException(
                    "Can't authenticate user with email " + email, new Exception());
        }
        return userOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isEmpty()) {
            throw new RegistrationException(
                    "User with email " + email + " already exists in DB!", new Exception());
        }
        return userService.add(new User(email, password));
    }
}
