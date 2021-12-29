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
        Optional<User> userFromDBoptional = userService.findByEmail(email);
        if (userFromDBoptional.isPresent()) {
            String hashedPassword =
                    HashUtil.hashPassword(password, userFromDBoptional.get().getSalt());
            if (userFromDBoptional.get().getPassword().equals(hashedPassword)) {
                return userFromDBoptional.get();
            }
        }
        throw new AuthenticationException("Can't authenticate user with such email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        Optional<User> userFromDBoptional = userService.findByEmail(email);
        if (!userFromDBoptional.isEmpty()) {
            throw new RegistrationException("User with this email: " + email + " is already exist");
        }
        if (password.isEmpty()) {
            throw new RegistrationException("Can't register user with empty password");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
