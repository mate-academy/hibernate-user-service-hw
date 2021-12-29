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
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent()
                && userFromDB.get().getPassword()
                    .equals(HashUtil.hashPassword(password, userFromDB.get().getSalt()))) {
            return userFromDB.get();
        }
        throw new AuthenticationException("Email or password is invalid");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (!userService.findByEmail(email).isEmpty()) {
            throw new RegistrationException("This email is already used!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
