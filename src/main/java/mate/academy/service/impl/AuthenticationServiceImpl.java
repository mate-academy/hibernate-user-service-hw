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
        Optional<User> userDB = userService.findByEmail(email);
        if (userDB.isPresent() && HashUtil.hashPassword(password, userDB.get().getSalt())
                .equals(userDB.get().getPassword())) {
            return userDB.get();
        }
        throw new AuthenticationException("Can`t authenticate user with email " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("The user with email " + email + " already exist");
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
