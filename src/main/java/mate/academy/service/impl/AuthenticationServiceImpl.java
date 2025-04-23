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
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new RegistrationException("Email and password cannot be empty");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("This login is already used");
        }
        User user = new User();
        user.setEmail(email);

        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);

        String hashedPassword = HashUtil.hashPassword(password, salt);
        user.setPassword(hashedPassword);
        userService.add(user);
        return user;

    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AuthenticationException("Incorrect email or password");
        }

        User user = optionalUser.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (!user.getPassword().equals(hashedPassword)) {
            throw new AuthenticationException("Incorrect email or password");
        }
        return user;
    }
}
