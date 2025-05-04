package mate.academy.service.impl;

import java.util.Optional;
import java.util.function.BiPredicate;
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
        BiPredicate<User, String> passValidator = (user, pass) -> user.getPassword().equals(
                HashUtil.hashPassword(pass, user.getSalt()));
        if (userFromDb.isPresent() && passValidator.test(userFromDb.get(), password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Wrong email address: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email address already exists: "
                    + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
