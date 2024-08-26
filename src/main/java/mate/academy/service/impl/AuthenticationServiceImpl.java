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
    private UserService userService = new UserServiceImpl();

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && isPasswordValid(password, user.get())) {
            return user.get();
        }
        throw new AuthenticationException("Can't login user: " + user);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            throw new RegistrationException("Can't register new user: " + user);
        }
        return userService.add(new User(email, password));
    }

    private boolean isPasswordValid(String password, User user) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }
}
