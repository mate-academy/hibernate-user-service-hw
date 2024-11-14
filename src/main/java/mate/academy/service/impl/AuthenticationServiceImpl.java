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
        Optional<User> userFromDboptional = userService.findByEmail(email);
        if (userFromDboptional.isEmpty()) {
            throw new AuthenticationException("Failed to authenticate user");
        }
        User user = userFromDboptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Failed to authenticate user");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty()) {
            return userService.add(user);
        } else {
            throw new RegistrationException("This email is already taken!");
        }
    }
}
