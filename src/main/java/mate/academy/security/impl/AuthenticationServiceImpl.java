package mate.academy.security.impl;

import jakarta.transaction.Transactional;
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
        if (userFromDbOptional.isEmpty()) {
            throw new AuthenticationException("Can not authenticate user");
        }
        User user = userFromDbOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can not authenticate user");
    }

    /**
     * We should register a new user. The new user entity will contain the email and password
     * @param email - user email. should be unique for each user
     * @param password - user password
     * @return new user instance
     */
    @Transactional
    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already in use");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.add(user);
        return userService.findByEmail(email).get();
    }
}
