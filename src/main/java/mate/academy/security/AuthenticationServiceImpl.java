package mate.academy.security;

import java.util.Optional;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.DataProcessingException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.User;
import mate.academy.service.UserService;
import mate.academy.util.HashUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByEmail(email);
        User user = userFromDb.orElseThrow(() ->
                new AuthenticationException("User not found."));
        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }

        throw new AuthenticationException("User email or password incorrect.");
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        User user = new User(email, password);

        try {
            return userService.add(user);
        } catch (DataProcessingException e) {
            throw new RegistrationException("Registration failed.", e);
        }
    }
}
