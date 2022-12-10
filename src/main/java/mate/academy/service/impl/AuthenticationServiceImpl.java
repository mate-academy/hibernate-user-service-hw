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
        Optional<User> userFromDb = userService.findByEmail(email);
        User user = userFromDb.get();
        if (user.getPassword().equals(HashUtil.hashPassword(password, user.getSalt()))) {
                return user;
        }
        throw new AuthenticationException("Can't login user, please check your email or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || email.isBlank()) {
            throw new RegistrationException("Can't register user. The user already registered");
        }
        return userService.add(new User(email,password));
    }
}
