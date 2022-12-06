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
        if (user.isEmpty() || !passwordCheck(user.get(), password)) {
            throw new AuthenticationException("Couldn't find user by email: " + email);
        }
        return user.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent() || email == null || password == null
                || password.isEmpty()) {
            throw new RegistrationException("Couldn't register new user");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }

    private boolean passwordCheck(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
