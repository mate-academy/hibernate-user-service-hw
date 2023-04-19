package mate.academy.service.impl;

import java.util.Objects;
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
        if (user.isPresent()) {
            User userFromDb = user.get();
            String hashedPassword = HashUtil.hashPassword(password, userFromDb.getHashingSalt());
            if (Objects.equals(hashedPassword, userFromDb.getPassword())) {
                return userFromDb;
            }
        }
        throw new AuthenticationException("Invalid login or password");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() || email.isEmpty()) {
            throw new RegistrationException("Please use another login");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Please use another, non-empty password");
        }
        return userService.add(new User(email, password));
    }
}
