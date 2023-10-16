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

        if (userFromDbOptional.isEmpty()
                || !userFromDbOptional.map(user -> user.getPassword()
                                .equals(HashUtil.hashPassword(password, user.getSalt())))
                        .orElse(false)) {
            throw new AuthenticationException("Can't authenticate user");
        }
        return userFromDbOptional.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(new User(email, password, HashUtil.getSalt()));
        }
        throw new RegistrationException("A user with this email: "
                + email + " already exists");
    }
}
