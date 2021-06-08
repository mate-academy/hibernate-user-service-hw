package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
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
        if (user.isPresent()
                && user.get().getPassword().equals(hashedPassword(user.get(), password))) {
            return user.get();
        }
        throw new AuthenticationException("Password or Email was wrong!");
    }

    @Override
    public User register(String email, String password) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Password or Email was wrong!");
        }
        User user = userOptional.get();
        if (user.getPassword().equals(hashedPassword(user, password))) {
            return user;
        }
        throw new AuthenticationException("Password or Email was wrong!");
    }

    private String hashedPassword(User user, String password) {
        return HashUtil.hashPassword(password, user.getSalt());
    }
}
