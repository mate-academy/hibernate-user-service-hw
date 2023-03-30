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
        Optional<User> byEmail = userService.findByEmail(email);
        if (byEmail.isEmpty() || !passwordComparing(password, byEmail.get())) {
            throw new AuthenticationException("Cant authenticate user");
        }
        return byEmail.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> byEmail = userService.findByEmail(email);
        if (!byEmail.isEmpty()) {
            throw new RegistrationException("Can't register user by email " + email);
        }
        User user = new User(email, password);
        userService.add(user);
        return user;
    }

    private boolean passwordComparing(String password, User user) {
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}
