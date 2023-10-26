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
        try {
            Optional<User> userFromDbOptional = userService.findByEmail(email);
            User user = userFromDbOptional.get();
            String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
            if (userFromDbOptional.isPresent() && user.getPasssword().equals(hashedPassword)) {
                return user;
            }
        } catch (NullPointerException e) {
            throw new AuthenticationException("Username or password not found");
        }
        return null;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(password, email);
        if (userService.findByEmail(email).isEmpty()) {
            return userService.add(user);
        }
        throw new RegistrationException("Can`t register user with email: " + email);
    }
}
