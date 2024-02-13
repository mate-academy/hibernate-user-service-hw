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
        Optional<User> userFromDb = userService.findByEmail(email);

        if (userFromDb.isPresent()) {
            String loginPassword = HashUtil.hashPassword(password, userFromDb.get().getSalt());

            if (loginPassword.equals(userFromDb.get().getPassword())) {
                return userFromDb.get();
            }
            throw new AuthenticationException("Incorrect password: " + password);
        }
        throw new AuthenticationException("Cannot find user by email: " + email);
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Invalid login or password");
        }
        if (userService.findByEmail(email).isPresent()) {
            throw new RegistrationException(String.format("User with %s email is present", email));
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.add(user);
    }
}
