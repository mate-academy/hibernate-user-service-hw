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
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            throw new AuthenticationException("User doesn't exist");
        }
        User userFound = user.get();
        String hashedPassword = HashUtil.hashPassword(password, userFound.getSalt());
        if (hashedPassword.equals(userFound.getPassword())) {
            return userFound;
        }
        throw new AuthenticationException("Invalid email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(password);
            newUser.setEmail(email);
            userService.add(newUser);
        }
        throw new RegistrationException("User with such email already exist");
    }
}
