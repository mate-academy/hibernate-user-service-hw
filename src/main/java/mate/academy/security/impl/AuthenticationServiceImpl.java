package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()
                && userFromDbOptional.get().getPassword()
                    .equals(HashUtil.hashPassword(password, userFromDbOptional.get().getSalt()))) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("Incorrect password or mail.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isEmpty()) {
            return userService.add(new User(email, password));
        }
        throw new RegistrationException("A user with email " + email + " already exists.");
    }
}
