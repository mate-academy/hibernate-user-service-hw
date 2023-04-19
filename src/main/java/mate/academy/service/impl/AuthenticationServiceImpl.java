package mate.academy.service.impl;

import java.util.Objects;
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
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Can't authenticate user with email: " + email);
        }
        User user = userOptional.get();
        String hashedPassword = HashUtil.getHashedPassword(password, user.getSalt());
        if (Objects.equals(user.getPassword(), hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can't authenticate user with email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email: " + email + "already exists");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
