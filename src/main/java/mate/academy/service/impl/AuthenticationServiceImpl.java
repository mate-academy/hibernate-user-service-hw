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
        if (user.isPresent()) {
            String hashedPassword =
                    HashUtil.hashPassword(password, user.get().getSalt());
            if (user.get().getPassword().equals(hashedPassword)) {
                return user.get();
            }
        }
        throw new AuthenticationException("Can't authenticate user with such email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> user = userService.findByEmail(email);
        if (!user.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Can't register user with email: " + email);
        }
        return userService.add(new User(email, password));
    }
}
