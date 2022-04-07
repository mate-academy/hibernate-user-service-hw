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
    private static final int SALT_SIZE = 16;
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && checkPassword(user.get(), password)) {
            return user.get();
        }
        throw new AuthenticationException("Email or password was incorrect");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email " + email + "is already used");
        }
        User user = new User();
        user.setEmail(email);
        user.setSalt(HashUtil.getSalt(SALT_SIZE));
        user.setPassword(HashUtil.hashPassword(password, user.getSalt()));
        return userService.add(user);
    }

    private Boolean checkPassword(User user, String password) {
        return HashUtil.hashPassword(password, user.getSalt()).equals(user.getPassword());
    }
}
