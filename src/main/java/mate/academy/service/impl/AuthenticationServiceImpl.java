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
        if (email != null && userFromDbOptional.isPresent() && password != null) {
            User userFromDB = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, userFromDB.getSalt());
            if (userFromDB.getPassword().equals(hashedPassword)) {
                return userFromDB;
            }
        }
        throw new AuthenticationException("Incorrect email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (password == null || password.isEmpty() || email == null || email.isEmpty()) {
            throw new RegistrationException("Invalid email or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email is already used");
        }
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userService.add(newUser);
    }
}
