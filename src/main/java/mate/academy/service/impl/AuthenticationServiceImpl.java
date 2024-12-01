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
        Optional<User> userFromDataBaseOptional = userService.findByEmail(email);
        if (userFromDataBaseOptional.isEmpty()) {
            throw new AuthenticationException("Can not authenticate user by email " + email);
        }
        User user = userFromDataBaseOptional.get();
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        throw new AuthenticationException("Can not authenticate user by password " + password);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null) {
            throw new RegistrationException("Can't register new user");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
