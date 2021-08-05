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
        Optional<User> userFromDb = userService.findByEmail(email);
        String hashedPassword = HashUtil.hashPassword(password, userFromDb.get().getSalt());
        if (userFromDb.isPresent()
                && userFromDb.get().getPassword().equals(hashedPassword)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Can't authenticate user!");
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new AuthenticationException("Can't register user!");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
