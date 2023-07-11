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
        Optional<User> userFromDbByEmail = userService.findByEmail(email);
        if (userFromDbByEmail.isPresent() && userFromDbByEmail.get().getPassword()
                .equals(HashUtil.hashPassword(password, userFromDbByEmail.get().getSalt()))) {
            return userFromDbByEmail.get();
        }
        throw new AuthenticationException("Can't get user by email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.isEmpty()) {
            throw new RegistrationException("Can't register user to DB");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
