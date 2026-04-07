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
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return optionalUser.get();

            }
        }

        throw new AuthenticationException("Wrong email or password!");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email.isBlank()) {
            throw new RegistrationException("Email cannot be blank!");
        }

        if (password.isBlank()) {
            throw new RegistrationException("Password cannot be blank!");
        }

        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already taken: " + email);
        }

        User user = new User();

        user.setPassword(password);
        user.setEmail(email);

        return userService.add(user);
    }
}
