package mate.academy.security.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userOptionalFromDb = userService.findByEmail(email);
        if (userOptionalFromDb.isEmpty() || !userOptionalFromDb.get().getPassword()
                .equals(HashUtil.hashedPassword(password,
                        userOptionalFromDb.get().getSalt()))) {
            throw new AuthenticationException("Email or password was incorrect");
        }
        return userOptionalFromDb.get();
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException("Email already exist: " + email);
        }
        User user = new User(email, password);
        return userService.add(user);
    }
}
