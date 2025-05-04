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
        Optional<User> userFromDmOptional = userService.findByEmail(email);

        if (userFromDmOptional.isPresent()) {
            User user = userFromDmOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
        }

        throw new AuthenticationException("Can`t login user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDmOptional = userService.findByEmail(email);
        if (userFromDmOptional.isEmpty()) {
            User user = new User(email, password);
            userService.add(user);
            return user;
        }

        throw new RegistrationException("Registration failed. Already exist user with email "
                + email);
    }
}
