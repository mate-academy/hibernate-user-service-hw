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
        Optional<User> storedUser = userService.findByEmail(email);
        if (storedUser.isEmpty()
                || !HashUtil.hashPassword(password, storedUser.get().getSalt())
                .equals(storedUser.get().getPassword())) {
            throw new AuthenticationException("Login or password are invalid");
        }
        return storedUser.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("User " + email + " already exists");
        }
        User user = new User();
        user.setLogin(email);
        user.setPassword(password);
        return user;
    }
}
