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
    private static UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDbOptional = userService.findByEmail(email);
        if (userFromDbOptional.isPresent()
                && HashUtil.hashPassword(password, userFromDbOptional.get().getSalt())
                        .equals(userFromDbOptional.get().getPassword())) {
            return userFromDbOptional.get();
        }
        throw new AuthenticationException("There is not such login " + email
                + "in DB, you should register first.");
    }

    @Override
    public User register(String email, String password) throws AuthenticationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new AuthenticationException("This user is already registered");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
