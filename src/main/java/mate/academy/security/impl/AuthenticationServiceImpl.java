package mate.academy.security.impl;

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
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        if (userFromDb.isPresent()) {
            throw new RegistrationException("User already exsits! ");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return userService.add(user);
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);

        if (userFromDb.isPresent() && userFromDb.get().getPassword()
                .equals(HashUtil.hashPassword(password,
                        userFromDb.get().getSalt()))) {
            return userFromDb.get();
        }

        throw new AuthenticationException("Can`t authenticate user! ");
    }
}
