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
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && validatePassword(user.get(), password)) {
            return user.get();
        }
        throw new AuthenticationException("Can't login user with email: " + email
                + ". Wrong email or password.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Can't register user with email: " + email
                    + ". User with such email is already existed.");
        }
        User user = new User(email, password);
        return userService.add(user);
    }

    private boolean validatePassword(User user, String password) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
