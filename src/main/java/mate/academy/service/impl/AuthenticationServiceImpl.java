package mate.academy.service.impl;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.service.UserService;
import mate.academy.util.HashUtilSha512;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private static UserService userService;

    @Override
    public User register(String email, String password)
            throws RegistrationException {
        if (userService.findByEmail(email).isPresent() || password.isEmpty()) {
            throw new RegistrationException("User with email " + email + " exist");
        }
        return userService.add(new User(email, password));
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.findByEmail(login);
        if (user.isPresent()
                && HashUtilSha512.hashPassword(user.get().getPassword(), user.get().getSalt())
                .equals(password)) {
            return user.get();
        }
        throw new AuthenticationException("Login or password is wrong ");
    }
}
