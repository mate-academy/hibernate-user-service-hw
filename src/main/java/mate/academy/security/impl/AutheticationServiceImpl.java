package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AutheticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AutheticationServiceImpl implements AutheticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("User with email '" + email + "' is not found");
        }

        User user = userOptional.get();
        String hashedPass = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPass)) {
            return user;
        }
        throw new AuthenticationException("Incorrect password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new RegistrationException("User with email '" + email + "' already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
