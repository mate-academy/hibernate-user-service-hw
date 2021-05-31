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
        Optional<User> userByEmail = userService.findByEmail(email);
        if (userByEmail.isPresent()
                && userByEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password, userByEmail.get().getSalt()))) {
            return userByEmail.get();
        }
        throw new AuthenticationException("Can't authenticate user with email: " + email);
    }

    @Override
    public User register(String email, String password) {
        User user = new User(email, password);
        return userService.add(user);
    }
}
